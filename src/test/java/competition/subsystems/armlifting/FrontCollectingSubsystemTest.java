package competition.subsystems.armlifting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.subsystems.armlifting.FrontCollectingSubsystem;
import xbot.common.injection.BaseWPITest;

public class FrontCollectingSubsystemTest extends BaseWPITest {
    @Test
    public void testFrontCollecting() {
        FrontCollectingSubsystem frontCollect = this.injector.getInstance(FrontCollectingSubsystem.class);
        frontCollect.intake();

        assertEquals(1, frontCollect.frontCollectingMotor.getMotorOutputPercent(), 0.001);
    }
}