package competition.subsystems.hood;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class HoodSubsystemTest extends BaseCompetitionTest {
    
    @Test
    public void testExtendHood() {
        HoodSubsystem hood = this.injector.getInstance(HoodSubsystem.class);
        hood.extend();

        assertEquals(1, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testRetractHood(){
        HoodSubsystem hood = this.injector.getInstance(HoodSubsystem.class);
        hood.retract();
        
        assertEquals(-1, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }

    
}
