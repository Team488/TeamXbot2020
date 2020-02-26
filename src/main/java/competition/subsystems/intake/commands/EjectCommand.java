package competition.subsystems.intake.commands;

import com.google.inject.Inject;

import competition.subsystems.intake.IntakeSubsystem;
import xbot.common.command.BaseCommand;

public class EjectCommand extends BaseCommand {

    private final IntakeSubsystem intake;

    @Inject
    public EjectCommand(IntakeSubsystem intake) {
        this.intake = intake;
        this.addRequirements(intake);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        intake.eject();
    }
}