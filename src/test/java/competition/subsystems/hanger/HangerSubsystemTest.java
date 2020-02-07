package competition.subsystems.hanger;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.hanger.HangerSubsystem;

public class HangerSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testHangerExtendHanger() {
        HangerSubsystem hanger = this.injector.getInstance(HangerSubsystem.class);
        hanger.extendHanger();
        assertEquals(1, hanger.hangerMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testRetractHanger() {
        HangerSubsystem hanger = this.injector.getInstance(HangerSubsystem.class);
        hanger.retractHanger();
        assertEquals(-1, hanger.hangerMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testHangerGrabClamp() {
        HangerSubsystem hanger = this.injector.getInstance(HangerSubsystem.class);
        hanger.grabClamp();
        assertEquals(1, hanger.hangerMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testHangerReleaseClamp() {
        HangerSubsystem hanger = this.injector.getInstance(HangerSubsystem.class);
        hanger.releaseClamp();
        assertEquals(-1, hanger.hangerMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testHangerStop() {
        HangerSubsystem hanger = this.injector.getInstance(HangerSubsystem.class);
        hanger.extendHanger();
        if (hanger.hangerMotor.getMotorOutputPercent() > 0) {
            hanger.stop();
        }
        
        assertEquals(0, hanger.hangerMotor.getMotorOutputPercent(), 0.001);
    }
}