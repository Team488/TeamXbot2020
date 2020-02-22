package competition.subsystems.turret;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.commands.TurretWaitForRotationToGoalCommand;

public class TurretWaitForRotationToGoalCommandTest extends BaseCompetitionTest {

    @Test
    public void testAtGoal() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);

        TurretWaitForRotationToGoalCommand command = this.injector.getInstance(TurretWaitForRotationToGoalCommand.class);

        turret.setGoalAngle(turret.getCurrentAngle() + 90);

        command.initialize();
        command.execute();

        assertFalse(command.isFinished());

        turret.setGoalAngle(turret.getCurrentAngle());

        turret.setMaintainerIsAtGoal(true);

        command.execute();

        assertTrue(command.isFinished());
    }

    @Test
    public void testTimeout() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);

        TurretWaitForRotationToGoalCommand command = this.injector.getInstance(TurretWaitForRotationToGoalCommand.class);

        turret.setGoalAngle(turret.getCurrentAngle() + 90);

        command.initialize();
        command.execute();

        assertFalse(command.isFinished());

        this.timer.advanceTimeInSecondsBy(10);

        assertTrue(command.isFinished());
    }

}