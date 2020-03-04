package competition.subsystems.internalconveyor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.internalconveyor.commands.KickerLiftCommand;

public class KickerLiftCommandTest extends BaseCompetitionTest{

    @Test
    public void kickerLiftWheel(){
        KickerLiftCommand lift = this.injector.getInstance(KickerLiftCommand.class);
        KickerSubsystem kicker = this.injector.getInstance(KickerSubsystem.class);

        lift.execute();

        assertEquals(1 , kicker.wheelMotor.getMotorOutputPercent(), 0.001);
    }

}