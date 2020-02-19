package competition.commandgroups;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.turret.commands.PointTurretToFieldOrientedHeadingCommand;
import competition.subsystems.turret.commands.TurretMaintainerCommand;
import competition.subsystems.turret.commands.TurretWaitForRotationToGoalCommand;
import edu.wpi.first.wpilibj.MockTimer;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;
import xbot.common.properties.PropertyFactory;

public class PassTowardsTargetCommandTest extends BaseCompetitionTest {

    private MockTimer timer;

    private TurretSubsystem turret;

    private TurretMaintainerCommand turretMaintainer;

    private PassTowardsTargetCommand command;
    private PointTurretToFieldOrientedHeadingCommand rotateTurretCommand;
    private TurretWaitForRotationToGoalCommand waitForRotateTurretCommand;

    @Override
    public void setUp() {
        super.setUp();

        PropertyFactory pf = this.injector.getInstance(PropertyFactory.class);
        this.timer = this.injector.getInstance(MockTimer.class);

        this.turret = this.injector.getInstance(TurretSubsystem.class);
        this.turretMaintainer = this.injector.getInstance(TurretMaintainerCommand.class);

        this.rotateTurretCommand = new PointTurretToFieldOrientedHeadingCommand(this.turret);
        this.waitForRotateTurretCommand = new TurretWaitForRotationToGoalCommand(pf, this.turret);

        rotateTurretCommand.setFieldOrientedGoal(180);

        this.command = new PassTowardsTargetCommand(rotateTurretCommand, waitForRotateTurretCommand);
    }

    @Test
    public void testCommand() {
        this.command.initialize();

        assertFalse(this.command.isFinished());

        this.command.execute();
        assertFalse(this.waitForRotateTurretCommand.isFinished());
        
        // rotate to the target angle
        setRawTurretAngle(this.turret, this.turret.getGoalAngle() - 90 /* default forward angle */);

        // persuade the maintainer that we're stable
        this.turretMaintainer.execute();
        this.timer.advanceTimeInSecondsBy(1.0);
        this.turretMaintainer.execute();

        this.command.execute();
        assertTrue(this.waitForRotateTurretCommand.isFinished());

    }

    private void setRawTurretAngle(TurretSubsystem turret, double angle) {
        double ticks = angle / turret.getTicksPerDegree();
        ((MockCANTalon)(turret.motor)).setPosition((int)ticks);
    }
}
