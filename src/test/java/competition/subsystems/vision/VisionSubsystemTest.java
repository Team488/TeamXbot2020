package competition.subsystems.vision;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.MockTimer;

public class VisionSubsystemTest extends BaseCompetitionTest {

    private VisionSubsystem vision;
    private NetworkTableInstance netTableInstance;
    protected MockTimer mockTimer;

    @Override
    public void setUp() {
        super.setUp();
        this.vision = this.injector.getInstance(VisionSubsystem.class);
        this.netTableInstance = this.injector.getInstance(NetworkTableInstance.class);

        this.mockTimer = this.injector.getInstance(MockTimer.class);        
        this.mockTimer.advanceTimeInSecondsBy(10);
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
}