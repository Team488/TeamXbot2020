package competition.subsystems.hood.commands;

import com.google.inject.Inject;

import competition.IdealElectricalContract;
import competition.operator_interface.OperatorInterface;
import competition.subsystems.hood.HoodSubsystem;
import xbot.common.command.BaseMaintainerCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.properties.PropertyFactory;

public class HoodMaintainerCommand extends BaseMaintainerCommand {

    private final HoodSubsystem hood;
    private final PIDManager pid;
    private final OperatorInterface oi;
    private final IdealElectricalContract contract;

    @Inject
    public HoodMaintainerCommand(HoodSubsystem hood, PropertyFactory pf, PIDFactory pidf, CommonLibFactory clf,
    OperatorInterface oi, IdealElectricalContract contract){
        super(hood, pf, clf, 1, 0.33);
        this.hood = hood;
        this.oi = oi;
        pid = pidf.createPIDManager("HoodPID", 0.04, 0, 0);
        this.contract = contract;
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    @Override
    protected void calibratedMachineControlAction() {
        if (!this.hood.isCalibrated()) {
            this.hood.stop();
            return;
        }

        double power = pid.calculate(hood.getGoalPercent(), hood.getPercentExtended());
        this.hood.setPower(power);
    }

    @Override
    protected void uncalibratedMachineControlAction() {
        if (this.hood.isCalibrated()) {
            return;
        }

        // Assume we are in fully retracted position if we don't have limit switch yet
        if (!this.contract.isHoodLimitSwitchReady()) {
            this.hood.calibrateHood();
        }

        if (!this.hood.isCalibrateInProgress()) {
            this.hood.setCalibrationStartTime();
        }

        if (!this.hood.isAtReverseLimit()
        && !this.hood.isCalibrateTimedOut()) {
            this.hood.retractForCalibration();
        } else {
            this.hood.stop();
            this.hood.calibrateHood();
        }
    }

    @Override
    protected double getHumanInput(){
        return MathUtils.deadband(oi.manualOperatorGamepad.getLeftVector().y*.25, oi.getJoystickDeadband());
    }
}