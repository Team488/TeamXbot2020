package competition.subsystems.hanger;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.hanger.HangerSubsystem;

public class HangerSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testHanger() {
        HangerSubsystem hanger = this.injector.getInstance(HangerSubsystem.class);
        hanger.extendHanger();
        assertEquals(1, hanger.hangerMotor.getMotorOutputPercent(), 0.001);
    }
}