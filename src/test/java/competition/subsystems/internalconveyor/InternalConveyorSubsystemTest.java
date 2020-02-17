package competition.subsystems.internalconveyor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class InternalConveyorSubsystemTest extends BaseCompetitionTest {
    
    @Test
    public void testIntake() {
        IndexerSubsystem internalConveyor = this.injector.getInstance(IndexerSubsystem.class);
        internalConveyor.lift();

        assertEquals(1, internalConveyor.motor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testOuttake(){
        IndexerSubsystem internalConveyor = this.injector.getInstance(IndexerSubsystem.class);
        internalConveyor.reverse();
        
        assertEquals(-0.25, internalConveyor.motor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testStop(){
        IndexerSubsystem internalConveyor = this.injector.getInstance(IndexerSubsystem.class);
        internalConveyor.stop();
        
        assertEquals(0, internalConveyor.motor.getMotorOutputPercent(), 0.001);
    }
    
}