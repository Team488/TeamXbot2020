package competition.subsystems.arm.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.arm.FrontCollectingSubsystem;
import xbot.common.command.BaseCommand;

public class FrontGrabbingBallsCommand extends BaseCommand {

    final FrontCollectingSubsystem frontCollectingSubsystem;
    final OperatorInterface oi;

    @Inject
    public FrontGrabbingBallsCommand(OperatorInterface oi, FrontCollectingSubsystem frontCollectingSubsystem) {
        this.oi = oi;
        this.frontCollectingSubsystem = frontCollectingSubsystem;
        this.addRequirements(this.frontCollectingSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        frontCollectingSubsystem.intake();
    }
    
}