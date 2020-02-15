package competition.subsystems.turret;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;

public class TurretSubsystemTest extends BaseCompetitionTest {

    private TurretSubsystem turret;

    @Override
    public void setUp() {
        super.setUp();
        turret = this.injector.getInstance(TurretSubsystem.class);
    }

    @Test
    public void testTurnRight() {
        turret.turnRight();

        assertEquals(-0.03 , turret.motor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testTurnLeft() {
        turret.turnLeft();

        assertEquals(0.03 , turret.motor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testStop() {
        turret.turnRight();
        assertTrue(turret.getDefaultTurretPower() > 0.01);
        assertEquals(-turret.getDefaultTurretPower(), turret.motor.getMotorOutputPercent(), 0.001);

        turret.stop();

        assertEquals(0 , turret.motor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testCalibration() {
        assertEquals(90, turret.getCurrentAngle(), 0.001);

        setRawTurretAngle(100);

        assertEquals(190, turret.getCurrentAngle(), 0.001);

        turret.calibrateTurret();

        assertEquals(90, turret.getCurrentAngle(), 0.001);
    }

    private void setRawTurretAngle(double angle) {
        double ticks = angle / turret.getTicksPerDegree();
        ((MockCANTalon)(turret.motor)).setPosition((int)ticks);
    }
}