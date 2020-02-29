package competition.subsystems.vision.commands;

import com.google.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;

public class SendInitialPositionToRaspberryCommand extends BaseCommand{

    VisionSubsystem b;
    PoseSubsystem pose;

    @Inject
    public SendInitialPositionToRaspberryCommand(){ // TODO: check if any thing is needed in the parameters (probably PropertyFactory)

    }

    @Override
    public void execute() {
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        double theta = pose.getCurrentFieldPose().getHeading().getValue(); // Theta value
        double x = pose.getCurrentFieldPose().getPoint().x; // XY Values
        double y = pose.getCurrentFieldPose().getPoint().y; // XY Values

        b.sendXYThetaPos(x, y, theta);

    }

}