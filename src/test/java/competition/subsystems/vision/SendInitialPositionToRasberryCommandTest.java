package competition.subsystems.vision;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.vision.commands.SendInitialPositionToRaspberryCommand;

public class SendInitialPositionToRasberryCommandTest extends BaseCompetitionTest{

    SendInitialPositionToRaspberryCommand initial;
    VisionSubsystem vision;
    PoseSubsystem pose;

    @Override
    public void setUp(){
        super.setUp();
        pose = this.injector.getInstance(PoseSubsystem.class);
        initial = this.injector.getInstance(SendInitialPositionToRaspberryCommand.class);
        vision = this.injector.getInstance(VisionSubsystem.class);
    }

    @Test
    public void testSendInitialPositionAll(){
        double theta = pose.getCurrentFieldPose().getHeading().getValue(); // Theta value
        double x = pose.getCurrentFieldPose().getPoint().x; // XY Values
        double y = pose.getCurrentFieldPose().getPoint().y; // XY Values

        initial.initialize();
        assertTrue("Should set the value Y to initialY", y == vision.initialY.get());
        assertTrue("Should set the value X to initialX", x == vision.initialX.get());
        assertTrue("Should set the value Theta to initialTheta", theta == vision.initialTheta.get());
    }

    @Test
    public void testSendInitialPositionX(){
        double x = pose.getCurrentFieldPose().getPoint().x; // XY Values

        initial.initialize();
        assertTrue("Should set the value X to initialX", x == vision.initialX.get());
    }

    @Test
    public void testSendInitialPositionY(){
        double y = pose.getCurrentFieldPose().getPoint().y; // XY Values

        initial.initialize();
        assertTrue("Should set the value Y to initialY", y == vision.initialY.get());
    }

    @Test
    public void testSendInitialPositionTheta(){
        double theta = pose.getCurrentFieldPose().getHeading().getValue(); // Theta value

        initial.initialize();
        assertTrue("Should set the value Theta to initialTheta", theta == vision.initialTheta.get());
    }
}