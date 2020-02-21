package competition.subsystems.arm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.arm.ArmSubsystem;

public class ArmSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testArmUp() {
        ArmSubsystem Arm = this.injector.getInstance(ArmSubsystem.class);
        Arm.up();
        assertEquals(true, Arm.armSolenoid.getAdjusted());
    }

    @Test
    public void testArmDown() {
        ArmSubsystem Arm = this.injector.getInstance(ArmSubsystem.class);
        Arm.down();
        assertEquals(false, Arm.armSolenoid.getAdjusted());
    }
}