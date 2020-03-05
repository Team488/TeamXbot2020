
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
    final HoodSubsystem hood;

    public DoubleProperty minAngle;
    public DoubleProperty maxAngle;
    public DoubleProperty maxVelocityProperty;
    public DoubleProperty heightToGoal;
    public DoubleProperty initialHeight;

    public double bestAngle;
    public double bestVelocity;

    @Inject
    public BestShotAdjustment( final PropertyFactory pf,  final ShooterWheelSubsystem wheel, 
                               final PoseSubsystem pose, final TurretSubsystem turret,
                               final HoodSubsystem hood, VisionSubsystem vision){
        this.vision = vision;
        this.hood = hood;
        this.wheel = wheel;
        this.pose = pose;
        this.turret = turret;

        minAngle = pf.createPersistentProperty("MinAngle", 0);
        maxAngle = pf.createPersistentProperty("MaxAngle", 60);
        maxVelocityProperty = pf.createPersistentProperty("Maxvelocity", 40);
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
        wheel.setTargetValue(wheel.velocityToRPM(bestVelocity));
    }

    public void findBestShot(double distance)
    {
        bestAngle = findAngle(distance);
        bestVelocity = findVelocity(distance);
    }

    public double findAngle(double distance)
    {
        return Math.atan(2*((heightToGoal.get() - initialHeight.get())/(distance)));
    }

    public double findVelocity(double distance) {
        double num = (Math.sqrt(64 * (heightToGoal.get() - initialHeight.get())));
        double den = Math.sin(findAngle(distance));
        return num/den;
    }

    public boolean shotIsPossible(double angle, double velosity)
    {
        boolean angleIsPossible = angle>=minAngle.get() && angle<=maxAngle.get();
        boolean velosityIsPossible = velosity > 0 && velosity < maxVelocityProperty.get();
        return angleIsPossible && velosityIsPossible;
    }


}
