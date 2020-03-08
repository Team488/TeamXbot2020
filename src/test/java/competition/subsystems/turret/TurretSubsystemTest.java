package competition.subsystems.turret;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.wpilibj.MockTimer;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;
import xbot.common.controls.sensors.mock_adapters.MockGyro;

public class TurretSubsystemTest extends BaseCompetitionTest {

    private TurretSubsystem turret;
    private PoseSubsystem pose;
    protected MockTimer mockTimer;

    @Override
    public void setUp() {
        super.setUp();
        turret = this.injector.getInstance(TurretSubsystem.class);
        pose = this.injector.getInstance(PoseSubsystem.class);

        mockTimer = injector.getInstance(MockTimer.class);        
        mockTimer.advanceTimeInSecondsBy(10);
        pose.periodic();
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

    @Test
    public void testUpperLimit() {
        turret.calibrateTurret();
        setRawTurretAngle(turret.getMaxAngle() - 90 + 1);
        verifyTurretAngle(turret.getMaxAngle() + 1);

        turret.setPower(1);
        verifyTurretPower(0);
        turret.setPower(-1);
        verifyTurretPower(-1);
    }

    @Test
    public void testLowerLimit() {
        turret.calibrateTurret();
        setRawTurretAngle(turret.getMinAngle() - 90 - 1);
        verifyTurretAngle(turret.getMinAngle() - 1);

        turret.setPower(1);
        verifyTurretPower(1);
        turret.setPower(-1);
        verifyTurretPower(0);
    }

    @Test
    public void testUncalibration() {
        turret.calibrateTurret();
        setRawTurretAngle(turret.getMaxAngle() - 90 + 1);
        verifyTurretAngle(turret.getMaxAngle() + 1);

        turret.setPower(1);
        verifyTurretPower(0);
        turret.uncalibrate();
        turret.setPower(1);
        verifyTurretPower(1);
    }

    @Test
    public void testFieldOrientedGoal() {
        verifyRobotHeading(90);
        // Robot pointed straight forward
        turret.setFieldOrientedGoalAngle(90);
        verifyTurretgoal(90);
        turret.setFieldOrientedGoalAngle(45);
        verifyTurretgoal(45);
        turret.setFieldOrientedGoalAngle(135);
        verifyTurretgoal(135);

        changeMockGyroHeading(45);
        verifyRobotHeading(135);
        // Robot pointed "Northwest"
        turret.setFieldOrientedGoalAngle(90);
        verifyTurretgoal(45);
        turret.setFieldOrientedGoalAngle(45);
        verifyTurretgoal(0);
        turret.setFieldOrientedGoalAngle(135);
        verifyTurretgoal(90);

        changeMockGyroHeading(-90);
        verifyRobotHeading(45);
        // Robot pointed "Northeast"
        turret.setFieldOrientedGoalAngle(90);
        verifyTurretgoal(135);
        turret.setFieldOrientedGoalAngle(45);
        verifyTurretgoal(90);
        turret.setFieldOrientedGoalAngle(135);
        verifyTurretgoal(180);

        changeMockGyroHeading(-90);
        verifyRobotHeading(-45);
        // Robot pointed "Southeast"
        turret.setFieldOrientedGoalAngle(90);
        verifyTurretgoal(225 - 360); // wrapped due to range
        turret.setFieldOrientedGoalAngle(45);
        verifyTurretgoal(180);
        turret.setFieldOrientedGoalAngle(135);
        verifyTurretgoal(270 - 360); // wrapped due to range

        changeMockGyroHeading(-90);
        verifyRobotHeading(-135);
        // Robot pointed "Southwest"
        turret.setFieldOrientedGoalAngle(90);
        verifyTurretgoal(-45);
        turret.setFieldOrientedGoalAngle(45);
        verifyTurretgoal(-90);
        turret.setFieldOrientedGoalAngle(135);
        verifyTurretgoal(0);
    }

    private void setRawTurretAngle(double angle) {
        double ticks = angle / turret.getTicksPerDegree();
        ((MockCANTalon)(turret.motor)).setPosition((int)ticks);
    }

    private void verifyTurretAngle(double angle) {
        assertEquals(angle, turret.getCurrentAngle(), 0.1);
    }

    private void verifyTurretgoal(double angle) {
        assertEquals(angle, turret.getGoalAngle(), 0.001);
    }

    private void verifyTurretPower(double power) {
        assertEquals(power , turret.motor.getMotorOutputPercent(), 0.001);
    }

    protected void setMockGyroHeading(double heading) {
        ((MockGyro)pose.imu).setYaw(heading);
    }
    
    protected void changeMockGyroHeading(double delta) {
        double oldHeading = ((MockGyro)pose.imu).getDeviceYaw();
        double newHeading = oldHeading + delta;
        setMockGyroHeading(newHeading);
    }
    
    protected void verifyRobotHeading(double expectedHeading) {
        assertEquals(expectedHeading, pose.getCurrentHeading().getValue(), 0.001);
    }

    @Test
    public void minLimitTest() {
        MockCANTalon motor = (MockCANTalon)turret.motor;
        motor.setReverseLimitSwitch(true);
        turret.setPower(-1);
        verifyTurretPower(0);
        motor.setReverseLimitSwitch(false);
        turret.setPower(1);
        verifyTurretPower(1);
    }

    @Test
    public void maxLimitTest() {
        MockCANTalon motor = (MockCANTalon)turret.motor;
        motor.setForwardLimitSwitch(true);
        turret.setPower(1);
        verifyTurretPower(0);
        motor.setForwardLimitSwitch(false);
        turret.setPower(1);
        verifyTurretPower(1);
    }
}