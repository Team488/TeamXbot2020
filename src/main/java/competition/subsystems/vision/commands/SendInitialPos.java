package competition.subsystems.vision.commands;

import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;

public class SendInitialPos extends BaseCommand{

    VisionSubsystem b;
    PoseSubsystem pose;

    // Might delete, constructor probably not needed
    // @Inject
    // public sendInitialPos(PropertyFactory pf){
    // }

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