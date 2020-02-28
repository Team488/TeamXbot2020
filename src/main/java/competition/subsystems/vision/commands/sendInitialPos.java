package competition.subsystems.vision.commands;

import com.google.inject.Inject;

import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.PropertyFactory;

public class sendInitialPos extends BaseCommand{

    VisionSubsystem b;
    PoseSubsystem pose;
    public double Theta;
    public double X;
    public double Y;

    @Inject
    public sendInitialPos(PropertyFactory pf){ // Might delete, constructor probably not needed

    }

    @Override
    public void execute() {
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        Theta = pose.getCurrentFieldPose().getHeading().getValue(); // Theta value
        X = pose.getCurrentFieldPose().getPoint().x; // XY Values
        Y = pose.getCurrentFieldPose().getPoint().y; // XY Values

        b.sendXYThetaPos(X, Y, Theta);

    }

}