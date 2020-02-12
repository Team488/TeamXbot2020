package competition.subsystems.turret;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class TurretSubsystemTest extends BaseCompetitionTest {

    @Test
    public void testTurnRight() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        turret.turnRight();

        assertEquals(-0.03 , turret.motor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testTurnLeft() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        turret.turnLeft();

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