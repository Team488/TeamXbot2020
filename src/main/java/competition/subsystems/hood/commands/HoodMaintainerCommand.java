package competition.subsystems.hood.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.hood.HoodSubsystem;
import xbot.common.command.BaseMaintainerCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.properties.PropertyFactory;

public class HoodMaintainerCommand extends BaseMaintainerCommand{

    final HoodSubsystem hood;
    final PIDManager pid;
    final OperatorInterface oi;

    @Inject
    public HoodMaintainerCommand(HoodSubsystem hood, PropertyFactory pf, PIDFactory pidf, CommonLibFactory clf,
    OperatorInterface oi){
        super(hood, pf, clf, 1, 0.33);
        this.hood = hood;
        this.oi = oi;
        pid = pidf.createPIDManager("HoodPID", 0.04, 0, 0);

    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    @Override
    protected void calibratedMachineControlAction(){
        double power = pid.calculate(hood.getGoalPercent(), hood.getPercentExtended());
        hood.setPower(power);
    }

    @Override
    protected double getHumanInput(){
        return MathUtils.deadband(oi.operatorGamepad.getLeftVector().y, oi.getJoystickDeadband());
    }
}