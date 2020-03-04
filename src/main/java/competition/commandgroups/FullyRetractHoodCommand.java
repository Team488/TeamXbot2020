package competition.commandgroups;

import com.google.inject.Inject;

import competition.subsystems.hood.HoodSubsystem;
import xbot.common.command.BaseSetpointCommand;

public class FullyRetractHoodCommand extends BaseSetpointCommand {

    private final HoodSubsystem hood;

    @Inject
    public FullyRetractHoodCommand(final HoodSubsystem hood) {
        super(hood);
        this.hood = hood;
    }

    @Override
    public void initialize() {
        // nothing to do
    }

    @Override
    public void execute() {
        this.hood.setGoalPercent(0);
    }
}