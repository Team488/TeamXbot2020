package competition.autonomous;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;

public class DoNothingInAutonomousCommand extends BaseCommand {

    @Inject
    public DoNothingInAutonomousCommand() {

    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}