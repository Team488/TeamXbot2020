package competition.subsystems.internalconveyor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class KickerSubsystemTest extends BaseCompetitionTest {
    
    @Test
    public void testIntake() {
        KickerSubsystem kicker = this.injector.getInstance(KickerSubsystem.class);
        kicker.lift();

        assertEquals(1, kicker.wheelMotor.getMotorOutputPercent(), 0.001);
        //assertEquals(1, internalConveyor.rollerMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testOuttake(){
        KickerSubsystem kicker = this.injector.getInstance(KickerSubsystem.class);
        kicker.reverse();
        
        assertEquals(-0.25, kicker.wheelMotor.getMotorOutputPercent(), 0.001);
        //assertEquals(-0.25, internalConveyor.rollerMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testStop(){
        KickerSubsystem kicker = this.injector.getInstance(KickerSubsystem.class);
        kicker.stop();
        
        assertEquals(0, kicker.wheelMotor.getMotorOutputPercent(), 0.001);
        //assertEquals(0, internalConveyor.rollerMotor.getMotorOutputPercent(), 0.001);
    }
    
}