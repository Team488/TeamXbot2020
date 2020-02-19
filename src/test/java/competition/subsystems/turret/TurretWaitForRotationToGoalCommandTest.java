package competition.subsystems.turret;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.commands.TurretMaintainerCommand;
import competition.subsystems.turret.commands.TurretWaitForRotationToGoalCommand;
import edu.wpi.first.wpilibj.MockTimer;
import xbot.common.properties.PropertyFactory;

public class TurretWaitForRotationToGoalCommandTest extends BaseCompetitionTest {

    @Test
    public void testAtGoal() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        PropertyFactory pf = this.injector.getInstance(PropertyFactory.class);
        MockTimer timer = this.injector.getInstance(MockTimer.class);

        // we need the maintainer to track stability
        TurretMaintainerCommand maintainerCommand = this.injector.getInstance(TurretMaintainerCommand.class);
        maintainerCommand.initialize();
        maintainerCommand.execute();

        TurretWaitForRotationToGoalCommand command = new TurretWaitForRotationToGoalCommand(pf, turret);

        turret.setGoalAngle(turret.getCurrentAngle() + 90);

        command.initialize();
        command.execute();

        assertFalse(command.isFinished());

        turret.setGoalAngle(turret.getCurrentAngle());

        // we need to show stability over time before the maintainer will say we've met our goal
        maintainerCommand.execute();
        timer.advanceTimeInSecondsBy(1.0);
        maintainerCommand.execute();

        command.execute();

        assertTrue(command.isFinished());
    }

    @Test
    public void testTimeout() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        PropertyFactory pf = this.injector.getInstance(PropertyFactory.class);

        TurretWaitForRotationToGoalCommand command = new TurretWaitForRotationToGoalCommand(pf, turret);

        turret.setGoalAngle(turret.getCurrentAngle() + 90);

        command.initialize();
        command.execute();

        assertFalse(command.isFinished());

        MockTimer mockTimer = injector.getInstance(MockTimer.class);        
        mockTimer.advanceTimeInSecondsBy(10);

        assertTrue(command.isFinished());
    }

}