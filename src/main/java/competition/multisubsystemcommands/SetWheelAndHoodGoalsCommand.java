package competition.multisubsystemcommands;

import com.google.inject.Inject;

import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class SetWheelAndHoodGoalsCommand extends BaseCommand {

    ShooterWheelSubsystem wheel;
    HoodSubsystem hood;

    private double wheelRpmGoal;
    private double hoodPercentGoal;

    private FieldPosition currentPosition;

    public enum FieldPosition {
        Custom, PointBlank, InitiationLaser, InitiationCloseToGoal, TrenchCloseToGoal, TrenchFarFromGoal, InitiationFarFromGoal, SafeMode
    }

    private final DoubleProperty pointBlankRpmProp;
    private final DoubleProperty pointBlankHoodProp;

    private final DoubleProperty initiationCloseToGoalRpmProp;
    private final DoubleProperty initiationClosetoGoalHoodProp;

    private final DoubleProperty initiationLaserRpmProp;
    private final DoubleProperty initiationLaserHoodProp;

    private final DoubleProperty trenchCloseToGoalRpmProp;
    private final DoubleProperty trenchCloseToGoalHoodProp;

    private final DoubleProperty trenchFarFromGoalRpmProp;
    private final DoubleProperty trenchFarFromGoalHoodProp;

    private final DoubleProperty initiationFarFromGoalRpmProp;
    private final DoubleProperty initiationFarfromGoalHoodProp;

    @Inject
    public SetWheelAndHoodGoalsCommand(ShooterWheelSubsystem wheel, HoodSubsystem hood, PropertyFactory pf) {
        this.wheel = wheel;
        this.hood = hood;
        pf.setPrefix(this.getClass().toString());
        currentPosition = FieldPosition.SafeMode;

        // TODO: Find values for this one.
        pointBlankRpmProp = pf.createPersistentProperty("Point Blank RPM", 2500);
        pointBlankHoodProp = pf.createPersistentProperty("Point Blank Hood", 0);

        // Lob shot
        initiationCloseToGoalRpmProp = pf.createPersistentProperty("Initiation CLOSE to Goal RPM", 2400);
        initiationClosetoGoalHoodProp = pf.createPersistentProperty("Initiation CLOSE to Goal Hood", 0.1);

        // Laser for autonomous
        initiationLaserRpmProp = pf.createPersistentProperty("Initiation LASER Goal RPM", 2400);
        initiationLaserHoodProp = pf.createPersistentProperty("Initiation LASER Goal Hood", 0.1);

        // Tested, works
        trenchCloseToGoalRpmProp = pf.createPersistentProperty("Trench CLOSE to Goal RPM", 3300);
        trenchCloseToGoalHoodProp = pf.createPersistentProperty("Trench CLOSE to Goal Hood", 0.49);

        // Doesn't seem possible.
        trenchFarFromGoalRpmProp = pf.createPersistentProperty("Trench FAR from Goal RPM", 4000);
        trenchFarFromGoalHoodProp = pf.createPersistentProperty("Trench FAR from Goal Hood", 0);

        // "Pass"
        initiationFarFromGoalRpmProp = pf.createPersistentProperty("Initiation FAR from Goal RPM", 3500);
        initiationFarfromGoalHoodProp = pf.createPersistentProperty("Initiation FAR from Goal Hood", 0.6);

        this.addRequirements(wheel.getSetpointLock(), hood.getSetpointLock());
    }

    @Override
    public String getName() {
        return super.getName() + "-" + currentPosition.toString();
    }

    public void setCustomGoals(double wheelRPM, double hoodPercent) {
        currentPosition = FieldPosition.Custom;
        setGoals(wheelRPM, hoodPercent);
    }

    private void setGoals(double wheelRPM, double hoodPercent) {
        this.wheelRpmGoal = wheelRPM;
        this.hoodPercentGoal = hoodPercent;
    }

    public void setGoals(FieldPosition position) {
        switch (position) {
        case PointBlank:
            setGoals(pointBlankRpmProp.get(), pointBlankHoodProp.get());
            break;
        case InitiationCloseToGoal:
            setGoals(initiationCloseToGoalRpmProp.get(), initiationClosetoGoalHoodProp.get());
            break;
        case InitiationLaser:
            setGoals(initiationLaserRpmProp.get(), initiationLaserHoodProp.get());
            break;
        case TrenchCloseToGoal:
            setGoals(trenchCloseToGoalRpmProp.get(), trenchCloseToGoalHoodProp.get());
            break;
        case TrenchFarFromGoal:
            setGoals(trenchFarFromGoalRpmProp.get(), trenchFarFromGoalHoodProp.get());
            break;
        case InitiationFarFromGoal: // aka "Pass"
            setGoals(initiationFarFromGoalRpmProp.get(), initiationFarfromGoalHoodProp.get());
            break;
        case SafeMode:
            setGoals(0, 0);
        default:
            // How did you get here?
            log.warn("Tried to aim at a position that doesn't have any properties ready!");
            setGoals(pointBlankRpmProp.get(), pointBlankHoodProp.get());
            break;
        }
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        log.info("RPM:" + wheelRpmGoal + ", Hood:" + hoodPercentGoal);
        wheel.setTargetRPM(wheelRpmGoal);
        hood.setGoalPercent(hoodPercentGoal);
    }

    @Override
    public void execute() {
        // nothing to do, intentionally
    }
}