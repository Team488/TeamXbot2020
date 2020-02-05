package competition.subsystems.armlifting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.armlifting.FrontCollectingSubsystem;

public class FrontCollectingSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testFrontCollecting() {
        FrontCollectingSubsystem frontCollect = this.injector.getInstance(FrontCollectingSubsystem.class);
        frontCollect.intake();
        assertEquals(1, frontCollect.frontCollectingMotor.getMotorOutputPercent(), 0.001);
    }
}