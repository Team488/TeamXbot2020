
package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class BestShotAdjustment extends BaseCommand{
   
    final ShooterWheelSubsystem wheel;
    final PoseSubsystem pose;
    final TurretSubsystem turret;
    final VisionSubsystem vision;
    final DoubleProperty minAngle;
    final DoubleProperty maxAngle;
    final DoubleProperty maxvelocityProperty;
    final DoubleProperty heightToGoal;
    final DoubleProperty incrementShotAdjustment;
    final DoubleProperty initialHeight;
    final HoodSubsystem hood;
    public double bestAngle;
    public double bestvelocity;


    @Inject
    public BestShotAdjustment( final PropertyFactory pf,  final ShooterWheelSubsystem wheel, 
                               final PoseSubsystem pose, final TurretSubsystem turret,
                               final HoodSubsystem hood, VisionSubsystem vision){
        this.vision = vision;
        this.hood = hood;
        this.wheel = wheel;
        this.pose = pose;
        this.turret = turret;
        incrementShotAdjustment = pf.createPersistentProperty("Increment Shot Adjustment", .5);
        minAngle = pf.createPersistentProperty("MinAngle", 0);
        maxAngle = pf.createPersistentProperty("MaxAngle", 60);
        maxvelocityProperty = pf.createPersistentProperty("Maxvelocity", 20);
        heightToGoal = pf.createPersistentProperty("Height of goal", 8.5);
        initialHeight = pf.createPersistentProperty("Initial Height", 2.5);
        this.addRequirements(this.wheel);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        findBestShot(vision.getDistanceToTarget());
    }

    public void execute(){
        hood.setAngle(bestAngle);
        wheel.setTargetValue(wheel.velocityToRPM(bestvelocity));
    }

    public void findBestShot(double distance)
    {
        double tvelocity = 4;
        double angle = (findAngleWvelocity(tvelocity, distance)/Math.PI) * 180;
        while(!(angleIsPossible(angle*Math.PI/180, distance)&& tvelocity <= 5400)){
            tvelocity += incrementShotAdjustment.get();
        }
        bestAngle = angle;
        bestvelocity = tvelocity;
    }

    public double findAngleWvelocity(final double velocity, final double distance)
    {
        return 0.5*Math.asin((32*distance)/(velocity * velocity));
    }

    public double findvelocityWAngle(final double theta, final double distance, final double initHeight){
        return Math.sqrt(1/(Math.pow(Math.cos(theta), 2)*(initHeight + (distance * Math.tan(theta)-heightToGoal.get()))));
    }

    public boolean angleIsPossible(final double angle, final double distance)
    {
        final double heightOfGoal = heightToGoal.get();
        final double heightAtPeak = initialHeight.get() + (maxvelocityProperty.get() * Math.sin(angle) * distance -(32) * Math.pow( distance, 2));

        return Math.abs(heightOfGoal-heightAtPeak) < .2;
    }


}
