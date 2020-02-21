package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.controls.sensors.XTimer;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class ShooterWheelWaitForGoalCommand extends BaseCommand {

    private final ShooterWheelSubsystem shooterWheelSubsystem;
    private final DoubleProperty timeoutProperty;

    private double startTime = -1;

    @Inject
    public ShooterWheelWaitForGoalCommand(PropertyFactory pf, ShooterWheelSubsystem subsystem) {
        this.shooterWheelSubsystem = subsystem;

        pf.setPrefix(this);

        this.timeoutProperty = pf.createPersistentProperty("Timeout", 1);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        this.startTime = XTimer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        // This command doesn't do anything
    }

    @Override
    public boolean isFinished() {
        return this.shooterWheelSubsystem.isMaintainerAtGoal() || XTimer.getFPGATimestamp() - this.startTime > this.timeoutProperty.get();
    }
    
}