package competition.subsystems.internalconveyor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class InternalConveyorSubsystemTest extends BaseCompetitionTest {
    
    @Test
    public void testIntake() {
        KickerSubsystem internalConveyor = this.injector.getInstance(KickerSubsystem.class);
        internalConveyor.lift();

        assertEquals(1, internalConveyor.wheelMotor.getMotorOutputPercent(), 0.001);
        //assertEquals(1, internalConveyor.rollerMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testOuttake(){
        KickerSubsystem internalConveyor = this.injector.getInstance(KickerSubsystem.class);
        internalConveyor.reverse();
        
        assertEquals(-0.25, internalConveyor.wheelMotor.getMotorOutputPercent(), 0.001);
        //assertEquals(-0.25, internalConveyor.rollerMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testStop(){
        KickerSubsystem internalConveyor = this.injector.getInstance(KickerSubsystem.class);
        internalConveyor.stop();
        
        assertEquals(0, internalConveyor.wheelMotor.getMotorOutputPercent(), 0.001);
        //assertEquals(0, internalConveyor.rollerMotor.getMotorOutputPercent(), 0.001);
    }
    
}