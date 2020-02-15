package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.logic.HumanVsMachineDecider;
import xbot.common.logic.HumanVsMachineDecider.HumanVsMachineMode;
import xbot.common.math.MathUtils;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.properties.PropertyFactory;

public class TurretMaintainerCommand extends BaseCommand {

    final TurretSubsystem turret;
    final PIDManager pid;
    final HumanVsMachineDecider decider;
    final OperatorInterface oi;

    @Inject
    public TurretMaintainerCommand(TurretSubsystem turret, PropertyFactory pf, PIDFactory pidf, CommonLibFactory clf, OperatorInterface oi) {
        this.turret = turret;
        this.oi = oi;
        decider = clf.createHumanVsMachineDecider(this.getPrefix());
        pid = pidf.createPIDManager("TurretPID", 0.04, 0, 0);
        this.addRequirements(turret);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        double power = 0;
        double humanInput = MathUtils.deadband(oi.operatorGamepad.getRightVector().x, oi.getJoystickDeadband());
        HumanVsMachineMode mode = decider.getRecommendedMode(humanInput);

        switch (mode) {
            case Coast:
                // Do nothing
                break;
            case HumanControl:
                power = humanInput;
                break;
            case InitializeMachineControl:
                turret.setGoalAngle(turret.getCurrentAngle());
                break;
            case MachineControl:
                if (turret.isCalibrated()){
                    power = pid.calculate(turret.getGoalAngle(), turret.getCurrentAngle());
                } else {
                    // TODO: Some kind of calibration routine. For now, direct human input.
                    power = humanInput;
                }
                break;
            default:
                // How did you get here?!?!
                break;
    
        }

        turret.setPower(power);
    }
}