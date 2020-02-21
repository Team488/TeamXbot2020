package competition.subsystems.shooterwheel;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.shooterwheel.commands.ShooterWheelWaitForGoalCommand;
import edu.wpi.first.wpilibj.MockTimer;
import xbot.common.properties.PropertyFactory;

public class ShooterWheelWaitForGoalCommandTest extends BaseCompetitionTest {

    @Test
    public void testAtGoal() {
        ShooterWheelSubsystem shooter = this.injector.getInstance(ShooterWheelSubsystem.class);
        PropertyFactory pf = this.injector.getInstance(PropertyFactory.class);

        ShooterWheelWaitForGoalCommand command = new ShooterWheelWaitForGoalCommand(pf, shooter);

        shooter.setTargetValue(9001);

        command.initialize();
        command.execute();

        assertFalse(command.isFinished());
        
        shooter.setMaintainerIsAtGoal(true);

        command.execute();

        assertTrue(command.isFinished());
    }

    @Test
    public void testTimeout() {
        ShooterWheelSubsystem shooter = this.injector.getInstance(ShooterWheelSubsystem.class);
        PropertyFactory pf = this.injector.getInstance(PropertyFactory.class);

        ShooterWheelWaitForGoalCommand command = new ShooterWheelWaitForGoalCommand(pf, shooter);

        shooter.setTargetRPM(9001);

        command.initialize();
        command.execute();

        assertFalse(command.isFinished());

        MockTimer mockTimer = injector.getInstance(MockTimer.class);        
        mockTimer.advanceTimeInSecondsBy(10);

        assertTrue(command.isFinished());
    }

}