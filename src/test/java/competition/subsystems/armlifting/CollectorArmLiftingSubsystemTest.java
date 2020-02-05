package competition.subsystems.armlifting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.armlifting.CollectorArmLiftingSubsystem;

public class CollectorArmLiftingSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testCollectorArmLifting() {
        CollectorArmLiftingSubsystem collectArmLifting = this.injector.getInstance(CollectorArmLiftingSubsystem.class);
        collectArmLifting.up();
        assertEquals(1, collectArmLifting.liftingCollectorArmMotor.getMotorOutputPercent(), 0.001);
    }
}