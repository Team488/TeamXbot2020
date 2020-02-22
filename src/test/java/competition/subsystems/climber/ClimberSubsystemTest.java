package competition.subsystems.climber;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.climber.ClimberSubsystem;

public class ClimberSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testExtendClimber() {
        ClimberSubsystem climber = this.injector.getInstance(ClimberSubsystem.class);
        climber.extendClimber();
        assertEquals(1, climber.leader.get(), 0.001);
    }

    @Test
    public void testRetractClimber() {
        ClimberSubsystem climber = this.injector.getInstance(ClimberSubsystem.class);
        climber.retractClimber();
        assertEquals(-1, climber.leader.get(), 0.001);
    }

    @Test
    public void testClimberGrabClamp() {
        ClimberSubsystem climber = this.injector.getInstance(ClimberSubsystem.class);
        climber.grabClamp();
        assertEquals(1, climber.leader.get(), 0.001);
    }

    @Test
    public void testClimberReleaseClamp() {
        ClimberSubsystem climber = this.injector.getInstance(ClimberSubsystem.class);
        climber.releaseClamp();
        assertEquals(-1, climber.leader.get(), 0.001);
    }

    @Test
    public void testClimberStop() {
        ClimberSubsystem climber = this.injector.getInstance(ClimberSubsystem.class);
        climber.extendClimber();
        assertEquals(1, climber.leader.get(), 0.001);
        if (climber.leader.get() > 0) {
            climber.stop();
        }
        
        assertEquals(0, climber.leader.get(), 0.001);
    }
}