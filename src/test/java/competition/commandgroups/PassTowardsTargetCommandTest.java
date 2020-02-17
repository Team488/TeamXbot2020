package competition.commandgroups;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.turret.commands.PointTurretToFieldOrientedHeadingCommand;
import competition.subsystems.turret.commands.TurretWaitForRotationToGoalCommand;
import edu.wpi.first.wpilibj.MockTimer;
import xbot.common.properties.PropertyFactory;

public class PassTowardsTargetCommandTest extends BaseCompetitionTest {

    private PassTowardsTargetCommand command;

    @Override
    public void setUp() {
        super.setUp();

        PropertyFactory pf = this.injector.getInstance(PropertyFactory.class);
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);

        PointTurretToFieldOrientedHeadingCommand rotateTurretCommand = new PointTurretToFieldOrientedHeadingCommand(turret);
        TurretWaitForRotationToGoalCommand waitForRotateTurretCommand = new TurretWaitForRotationToGoalCommand(pf, turret);

        rotateTurretCommand.setFieldOrientedGoal(180);

        this.command = new PassTowardsTargetCommand(rotateTurretCommand, waitForRotateTurretCommand);
    }

    @Test
    public void testTimeout() {
        MockTimer mockTimer = this.injector.getInstance(MockTimer.class);        
        double startTime = mockTimer.getFPGATimestamp();

        this.command.initialize();

        assertFalse(this.command.isFinished());

        do {
            this.command.execute();
            mockTimer.advanceTimeInSecondsBy(0.1);
        } while (!this.command.isFinished() && (mockTimer.getFPGATimestamp() - startTime < 10));

        assertTrue(this.command.isFinished());
    }
}
