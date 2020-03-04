package competition.subsystems.intake;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.intake.IntakeSubsystem;
import competition.subsystems.intake.commands.IntakeStutterCommand;

public class IntakeStutterCommandTest extends BaseCompetitionTest {

    @Test
    public void testStateOverTime() {
        IntakeSubsystem intake = this.injector.getInstance(IntakeSubsystem.class);
        IntakeStutterCommand command = this.injector.getInstance(IntakeStutterCommand.class);

        command.initialize();

        timer.setTimeInSeconds(0);
        command.execute();
        assertEquals(1, intake.intakeMotor.get(), 0.001);

        timer.advanceTimeInSecondsBy(0.81);
        command.execute();
        assertEquals(0, intake.intakeMotor.get(), 0.001);

        timer.advanceTimeInSecondsBy(0.21);
        command.execute();
        assertEquals(1, intake.intakeMotor.get(), 0.001);
    }

    @Test
    public void testRepeatingCommandGroup() {
        IntakeStutterCommand command = this.injector.getInstance(IntakeStutterCommand.class);

        command.initialize();

        timer.setTimeInSeconds(0);
        while (timer.getFPGATimestamp() < 10) {
            command.execute();
            timer.advanceTimeInSecondsBy(0.1);
        }

        assertFalse(command.isFinished());
    }

}