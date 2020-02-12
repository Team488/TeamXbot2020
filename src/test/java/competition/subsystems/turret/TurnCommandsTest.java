package competition.subsystems.turret;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.commands.TurnLeft;
import competition.subsystems.turret.commands.TurnRight;

public class TurnCommandsTest extends BaseCompetitionTest {

    @Test
    public void testTurnLeft() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);

        TurnLeft turnLeft = new TurnLeft(turret);
        turnLeft.initialize();
        turnLeft.execute();
        assertEquals(0.03 , turret.motor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testTurnRight() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        TurnRight turnRight = new TurnRight(turret);
        turnRight.initialize();
        turnRight.execute();

        assertEquals(0.03 , turret.motor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testStop() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        turret.turnRight();
        turret.stop();

        assertEquals(0 , turret.motor.getMotorOutputPercent(), 0.001);
    }

}