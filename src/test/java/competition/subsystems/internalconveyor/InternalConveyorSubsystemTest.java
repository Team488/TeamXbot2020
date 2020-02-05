package competition.subsystems.internalconveyor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class InternalConveyorSubsystemTest extends BaseCompetitionTest {
    
    @Test
    public void testIntake() {
        InternalConveyorSubsystem internalConveyor = this.injector.getInstance(InternalConveyorSubsystem.class);
        internalConveyor.intake();

        assertEquals(0.5, internalConveyor.intakeMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testOuttake(){
        InternalConveyorSubsystem internalConveyor = this.injector.getInstance(InternalConveyorSubsystem.class);
        internalConveyor.outtake();
        
        assertEquals(-0.5, internalConveyor.intakeMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testStop(){
        InternalConveyorSubsystem internalConveyor = this.injector.getInstance(InternalConveyorSubsystem.class);
        internalConveyor.stop();
        
        assertEquals(0, internalConveyor.intakeMotor.getMotorOutputPercent(), 0.001);
    }
    
}