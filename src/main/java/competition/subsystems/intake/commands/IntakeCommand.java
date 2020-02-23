package competition.subsystems.intake.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.intake.IntakeSubsystem;
import xbot.common.command.BaseCommand;

public class IntakeCommand extends BaseCommand {

    final IntakeSubsystem intakeSubsystem;
    final OperatorInterface oi;

    @Inject
    public IntakeCommand(OperatorInterface oi, IntakeSubsystem intakeSubsystem) {
        this.oi = oi;
        this.intakeSubsystem = intakeSubsystem;
        this.addRequirements(this.intakeSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        intakeSubsystem.intake();
    }
    
}