package competition.subsystems.vision.commands;

import com.google.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;

public class SendInitialPositionToRaspberryCommand extends BaseCommand{

    VisionSubsystem vision;
    PoseSubsystem pose;

    @Inject
    public SendInitialPositionToRaspberryCommand(VisionSubsystem eyes, PoseSubsystem pos){
        this.vision = eyes;
        this.pose = pos;
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

        vision.sendXYThetaPos(x, y, theta);

    }

}