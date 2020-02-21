
package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class BestShotAdjustment extends BaseCommand{
   
    final ShooterWheelSubsystem wheel;
    final PoseSubsystem pose;
    final TurretSubsystem turret;
    final DoubleProperty minAngle;
    final DoubleProperty maxAngle;
    final DoubleProperty maxVelosityProperty;
    final DoubleProperty heightToGoal;
    final DoubleProperty incrementShotAdjustment;
    final DoubleProperty initialHeight;
    final HoodSubsystem hood;
    public double tvelosity;


    @Inject
    public BestShotAdjustment( final PropertyFactory pf,  final ShooterWheelSubsystem wheel, 
                               final PoseSubsystem pose, final TurretSubsystem turret,
                               final HoodSubsystem hood){
        this.hood = hood;
        this.wheel = wheel;
        this.pose = pose;
        this.turret = turret;
        incrementShotAdjustment = pf.createPersistentProperty("Increment Shot Adjustment", 400);
        minAngle = pf.createPersistentProperty("MinAngle", 0);
        maxAngle = pf.createPersistentProperty("MaxAngle", 60);
        maxVelosityProperty = pf.createPersistentProperty("MaxVelosity", 20);
        heightToGoal = pf.createPersistentProperty("Height of goal", 6.5);
        tvelosity = 300;
        initialHeight = pf.createPersistentProperty("Initial Height", 2.5);
        this.addRequirements(this.wheel);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    public void execute(){
        final double distance = 10; //TODO: implement vision to find distance away from the goal
        double angle = (findAngleWVelosity(tvelosity, distance)/Math.PI) * 180;
        if(angleIsPossible(angle*Math.PI/180, distance)){
            hood.setAngle(angle);
            wheel.setPidSetpoint(ShooterWheelSubsystem.velosityToRPM(findVelosityWAngle(angle*Math.PI/180, distance, initialHeight.get())));
        }
        else{
            tvelosity+= incrementShotAdjustment.get();
        }
    }

    public double findAngleWVelosity(final double velosity, final double distance)
    {
        return 0.5*Math.asin((32*distance)/(velosity * velosity));
    }

    public double findVelosityWAngle(final double theta, final double distance, final double initHeight){
        return Math.sqrt(1/(Math.pow(Math.cos(theta), 2)*(initHeight + (distance * Math.tan(theta)-heightToGoal.get()))));
    }

    public boolean angleIsPossible(final double angle, final double distance)
    {
        final double heightOfGoal = heightToGoal.get();
        final double heightAtPeak = distance * Math.tan(angle) - (32)*(distance*distance)*(1/((maxVelosityProperty.get()*maxVelosityProperty.get())));

        return (heightOfGoal-heightAtPeak) < -.2;
    }


}
