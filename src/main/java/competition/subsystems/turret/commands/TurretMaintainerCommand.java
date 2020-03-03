package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseMaintainerCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.logic.HumanVsMachineDecider;
import xbot.common.math.MathUtils;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.properties.PropertyFactory;

public class TurretMaintainerCommand extends BaseMaintainerCommand {

    final TurretSubsystem turret;
    final PIDManager pid;
    final HumanVsMachineDecider decider;
    final OperatorInterface oi;

    @Inject
    public TurretMaintainerCommand(TurretSubsystem turret, PropertyFactory pf, PIDFactory pidf, CommonLibFactory clf, OperatorInterface oi) {
        super(turret, pf, clf, 1, 0.33);
        this.turret = turret;
        this.oi = oi;
        decider = clf.createHumanVsMachineDecider(this.getPrefix());
        pid = pidf.createPIDManager(this.getPrefix()+"TurretPID", 0.04, 0, 0);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    protected void calibratedMachineControlAction() {
        double power = pid.calculate(turret.getGoalAngle(), turret.getCurrentAngle());
        turret.setPower(power);
    }

    @Override
    protected double getHumanInput() {
        double operatorPower =  MathUtils.deadband(oi.operatorGamepad.getRightVector().x, oi.getJoystickDeadband());
        double manualPower = MathUtils.deadband(oi.manualOperatorGamepad.getRightVector().x, oi.getJoystickDeadband());
        return operatorPower + manualPower;
    }
}