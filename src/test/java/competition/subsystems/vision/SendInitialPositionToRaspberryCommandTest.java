package competition.subsystems.vision;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.vision.commands.SendInitialPositionToRaspberryCommand;

public class SendInitialPositionToRaspberryCommandTest extends VisionSubsystemTest{

    SendInitialPositionToRaspberryCommand initial;
    VisionSubsystem vision;
    PoseSubsystem pose;


    @Override
    public void setUp(){
        super.setUp();
        vision = this.injector.getInstance(VisionSubsystem.class);
        pose = this.injector.getInstance(PoseSubsystem.class);
        initial = this.injector.getInstance(SendInitialPositionToRaspberryCommand.class);
    }

    @Test
    public void testSendInitialPositionAll(){
        final double x = 2.0;
        final double theta = 4.0;
        final double y = 3.0;

        vision.sendXYThetaPos(x, y, theta);
        assertTrue("Should set the value Y to initialY", y == vision.initialY.get());
        assertTrue("Should set the value X to initialX", x == vision.initialX.get());
        assertTrue("Should set the value Theta to initialTheta", theta == vision.initialTheta.get());
    }

    @Test
    public void testSendInitialPositionX(){
        final double x = 2.0;

        vision.sendXYThetaPos(x, 0, 0);
        assertTrue("Should set the value X to initialX", x == vision.initialX.get());
    }

    @Test
    public void testSendInitialPositionY(){
        final double y = 3.0;

        vision.sendXYThetaPos(0, y, 0);
        assertTrue("Should set the value Y to initialY", y == vision.initialY.get());
    }

    @Test
    public void testSendInitialPositionTheta(){
        final double theta = 4.0;

        vision.sendXYThetaPos(0, 0, theta);
        assertTrue("Should set the value Theta to initialTheta", theta == vision.initialTheta.get());
    }
}