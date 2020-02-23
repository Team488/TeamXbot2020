package competition.subsystems.arm;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.intake.IntakeSubsystem;

public class IntakeSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testIntake() {
        IntakeSubsystem intake = this.injector.getInstance(IntakeSubsystem.class);
        intake.intake();
        assertEquals(1, intake.leftIntakeMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testIntakeIsAtCapacity() {
        IntakeSubsystem intake = this.injector.getInstance(IntakeSubsystem.class);
        intake.setCurrentTotalBalls(6);
        intake.setPower(1);
        assertEquals(0, intake.leftIntakeMotor.getMotorOutputPercent(), 0.001);
    }

    
    @Test
    public void testIntakeStop() {
        IntakeSubsystem intake = this.injector.getInstance(IntakeSubsystem.class);
        intake.intake();
        assertEquals(1, intake.leftIntakeMotor.getMotorOutputPercent(), 0.001);
        if (intake.leftIntakeMotor.getMotorOutputPercent() > 0) {
            intake.stop();
        }
        
        assertEquals(0, intake.leftIntakeMotor.getMotorOutputPercent(), 0.001);
    }

}