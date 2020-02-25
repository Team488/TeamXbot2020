package competition.subsystems.arm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.arm.ArmSubsystem;

public class ArmSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testArmUp() {
        ArmSubsystem arm = this.injector.getInstance(ArmSubsystem.class);
        arm.up();
        assertEquals(false, arm.armSolenoid.getAdjusted());
    }

    @Test
    public void testArmDown() {
        ArmSubsystem arm = this.injector.getInstance(ArmSubsystem.class);
        arm.down();
        assertEquals(true, arm.armSolenoid.getAdjusted());
    }
}