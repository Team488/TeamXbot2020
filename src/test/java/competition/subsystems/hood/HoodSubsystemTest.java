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

    @Test
    public void testIsFullyExtended(){
        HoodSubsystem hood = this.injector.getInstance(HoodSubsystem.class);
        //Max Angle is 5 currrently
        hood.setAngle(5);
        hood.setPower(1); //tries to extend it past max
        
        assertEquals(0, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testIsFullyExtracted(){
        HoodSubsystem hood = this.injector.getInstance(HoodSubsystem.class);
        //Min Angle is -5 currently
        hood.setAngle(-5);
        hood.setPower(-1); //tries to retract it past min
        
        assertEquals(0, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }
    
}
