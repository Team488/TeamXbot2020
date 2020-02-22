package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseCommand;

public class TimedShooterWheelCommand extends BaseCommand {

    final ShooterWheelSubsystem shooterWheelSubsystem;
    final OperatorInterface oi;

    @Inject
    public TimedShooterWheelCommand(OperatorInterface oi, ShooterWheelSubsystem shooterWheelSubsystem) {
        this.oi = oi;
        this.shooterWheelSubsystem = shooterWheelSubsystem;
        this.addRequirements(this.shooterWheelSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        shooterWheelSubsystem.timedShooting();
    }
}