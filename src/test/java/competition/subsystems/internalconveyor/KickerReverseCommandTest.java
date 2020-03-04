package competition.subsystems.internalconveyor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.internalconveyor.commands.KickerReverseCommand;

public class KickerReverseCommandTest extends BaseCompetitionTest{

    @Test
    public void kickerReverseWheel(){
        KickerReverseCommand reverse = this.injector.getInstance(KickerReverseCommand.class);
        KickerSubsystem kicker = this.injector.getInstance(KickerSubsystem.class);

        reverse.execute();

        assertEquals(-0.25 , kicker.wheelMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void kickerReverseRoller(){
        KickerSubsystem kicker = this.injector.getInstance(KickerSubsystem.class);
        KickerReverseCommand reverse = this.injector.getInstance(KickerReverseCommand.class);

        reverse.execute();

        assertEquals(-0.25, kicker.wheelMotor.getMotorOutputPercent(), 0.001);
    }
}