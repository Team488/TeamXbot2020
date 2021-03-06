package competition.subsystems.vision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.MockTimer;

public class VisionSubsystemTest extends BaseCompetitionTest {

    private VisionSubsystem vision;
    private PoseSubsystem pose;
    private NetworkTableInstance netTableInstance;
    protected MockTimer mockTimer;

    @Override
    public void setUp() {
        super.setUp();
        this.vision = this.injector.getInstance(VisionSubsystem.class);
        this.pose = this.injector.getInstance(PoseSubsystem.class);
        this.netTableInstance = this.injector.getInstance(NetworkTableInstance.class);

        this.mockTimer = this.injector.getInstance(MockTimer.class);
        this.mockTimer.advanceTimeInSecondsBy(10);
    }

    @Test
    public void testPosePublished() {

        while (!this.pose.getNavXReady()) {
            this.pose.periodic();
            this.vision.periodic();
            this.timer.advanceTimeInSecondsBy(0.1);
        }

        assertTrue(this.netTableInstance.getTable("pose").getEntry("navReady").getBoolean(false));
    }

    @Test
    public void testIsAmbanActive() {
        assertFalse("Uninitialized netTable state should be false", this.vision.isAmbanActive());

        this.netTableInstance.getTable("amban").getEntry("active").setBoolean(true);
        assertTrue("State should be true when netTable set to true", this.vision.isAmbanActive());

        this.netTableInstance.getTable("amban").getEntry("active").setBoolean(false);
        assertFalse("State should be false when netTable set to false", this.vision.isAmbanActive());
    }

    @Test
    public void testIsAmbanFixAcquired() {
        assertFalse("Uninitialized netTable state should be false", this.vision.isAmbanFixAcquired());

        this.netTableInstance.getTable("amban").getEntry("fixAcquired").setBoolean(true);
        assertTrue("State should be true when netTable set to true", this.vision.isAmbanFixAcquired());

        this.netTableInstance.getTable("amban").getEntry("fixAcquired").setBoolean(false);
        assertFalse("State should be false when netTable set to false", this.vision.isAmbanFixAcquired());
    }

    @Test
    public void testGetAmbanYawToTarget() {
        assertEquals("Uninitialized netTable state should be 0", 0, this.vision.getAmbanYawToTarget(), 0.01);

        this.netTableInstance.getTable("amban").getEntry("yawToTarget").setNumber(1.2);
        assertEquals("State should be match netTable", 1.2, this.vision.getAmbanYawToTarget(), 1.2);
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