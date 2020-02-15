
package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

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
    public double tvelosity;


    @Inject
    public BestShotAdjustment( PropertyFactory pf,  ShooterWheelSubsystem wheel, PoseSubsystem pose, TurretSubsystem turret){
        this.wheel = wheel;
        this.pose = pose;
        this.turret = turret;
        minAngle = pf.createPersistentProperty("MinAngle", 0);
        maxAngle = pf.createPersistentProperty("MaxAngle", 60);
        maxVelosityProperty = pf.createPersistentProperty("MaxVelosity", 20);
        tvelosity = 300;
        this.addRequirements(this.wheel);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    public void execute(){
        double distance = 10; //TODO: implement vision to find distance away from the goal
        if(angleIsPossible(findAngleWVelosity(tvelosity, distance), distance)){
            wheel.setPidSetpoint(tvelocity);
        }
        else{
            
        }
    }

    public double findAngleWVelosity(double velosity, double distance)
    {
        return 0.5*Math.asin((32*distance)/(velosity * velosity));
    }

    public boolean angleIsPossible(double angle, double distance)
    {
        double heightOfGoal = 8;
        double heightAtPeak = distance * Math.tan(angle) - (32)*(distance*distance)*(1/((maxVelosityProperty.get()*maxVelosityProperty.get())));

        return (heightOfGoal-heightAtPeak) < -.3;
    }
}
