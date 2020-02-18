package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseMaintainerCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.PropertyFactory;

public class ShooterWheelMaintainerCommand extends BaseMaintainerCommand {
   
    final ShooterWheelSubsystem wheel;
    final OperatorInterface oi;

    @Inject
    public ShooterWheelMaintainerCommand(OperatorInterface oi, ShooterWheelSubsystem wheel, PropertyFactory pf, CommonLibFactory clf){
        super(wheel, pf, clf, 50, 0.25);
        this.oi = oi;
        this.wheel = wheel;
        this.addRequirements(this.wheel);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        wheel.setCurrentLimits();
        wheel.configurePID();
    }

    @Override
    protected void maintain() {
        double speed = wheel.getTargetRPM();
        wheel.setPidSetpoint(speed);
    }

    @Override
    public void end(boolean interrupted) {
        wheel.resetWheel();
    }

    @Override
    protected void calibratedMachineControlAction() {
        double speed = wheel.getTargetRPM();
        wheel.setPidSetpoint(speed);
    }

    @Override
    protected double getHumanInput() {
        return 0;
    }
}
