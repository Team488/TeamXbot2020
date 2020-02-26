package competition.commandgroups;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.carousel.CarouselSubsystem;
import competition.subsystems.internalconveyor.KickerSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.shooterwheel.commands.ShooterWheelWaitForGoalCommand;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.turret.commands.PointTurretToFieldOrientedHeadingCommand;
import competition.subsystems.turret.commands.TurretWaitForRotationToGoalCommand;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;

public class PassTowardsTargetCommandTest extends BaseCompetitionTest {

    private TurretSubsystem turret;
    private ShooterWheelSubsystem shooter;
    private KickerSubsystem kicker;
    private CarouselSubsystem carousel;

    private PassTowardsTargetCommand command;
    private PointTurretToFieldOrientedHeadingCommand rotateTurretCommand;
    private TurretWaitForRotationToGoalCommand waitForRotateTurretCommand;
    private ShooterWheelWaitForGoalCommand shooterWaitCommand;

    @Override
    public void setUp() {
        super.setUp();

        this.turret = this.injector.getInstance(TurretSubsystem.class);
        this.shooter = this.injector.getInstance(ShooterWheelSubsystem.class);
        this.kicker = this.injector.getInstance(KickerSubsystem.class);
        this.carousel = this.injector.getInstance(CarouselSubsystem.class);

        this.rotateTurretCommand = this.injector.getInstance(PointTurretToFieldOrientedHeadingCommand.class);
        this.waitForRotateTurretCommand = this.injector.getInstance(TurretWaitForRotationToGoalCommand.class);
        this.shooterWaitCommand = this.injector.getInstance(ShooterWheelWaitForGoalCommand.class);

        rotateTurretCommand.setFieldOrientedGoal(180);

        this.command = this.injector.getInstance(PassTowardsTargetCommand.class);
    }

    @Test
    public void testCommand() {
        this.command.initialize();

        assertFalse(this.command.isFinished());

        this.command.execute();
        assertFalse(this.waitForRotateTurretCommand.isFinished());
        
        // rotate to the target angle
        setRawTurretAngle(this.turret, this.turret.getGoalAngle() - 90 /* default forward angle */);

        this.turret.setMaintainerIsAtGoal(true);

        this.command.execute();
        assertTrue(this.waitForRotateTurretCommand.isFinished());

        this.shooter.setMaintainerIsAtGoal(true);
        
        this.command.execute();
        assertTrue(this.shooterWaitCommand.isFinished());

        // check that the kicker and carousel started
        this.command.execute();
        assertTrue(((MockCANTalon)(this.kicker.wheelMotor)).getSetpoint() != 0);
        assertTrue(((MockCANTalon)(this.carousel.carouselMotor)).getSetpoint() != 0);
    }

    private void setRawTurretAngle(TurretSubsystem turret, double angle) {
        double ticks = angle / turret.getTicksPerDegree();
        ((MockCANTalon)(turret.motor)).setPosition((int)ticks);
    }
}
