package competition.subsystems.climber;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.climber.ClimberSubsystem;
import xbot.common.controls.actuators.mock_adapters.MockCANSparkMax;

public class ClimberSubsystemTest extends BaseCompetitionTest {
    
    ClimberSubsystem climber;

    @Override
    public void setUp() {
        super.setUp();
        climber = this.injector.getInstance(ClimberSubsystem.class);
        double safeZone = climber.getSlowZoneRange() + 20;
        setClimberPosition(safeZone, safeZone);
    }
    
    @Test
    public void testExtendClimber() {
        climber.extend();
        verifyPower(climber.getDefaultPower(), climber.getDefaultPower());
    }

    @Test
    public void testRetractClimber() {
        climber.retract();
        verifyPower(-climber.getDefaultPower(), -climber.getDefaultPower());
    }

    @Test
    public void testClimberStop() {
        climber.extend();
        verifyPower(climber.getDefaultPower(), climber.getDefaultPower());

        climber.stop();
        verifyPower(0, 0);
    }

    @Test
    public void testLowerLimit() {
        setClimberPosition(-5, getSafeRange());
        climber.retract();
        verifyPower(0, -climber.getDefaultPower());

        setClimberPosition(getSafeRange(), -5);
        climber.retract();
        verifyPower(-climber.getDefaultPower(), 0);
    }

    @Test
    public void testSlowZone() {
        setClimberPosition(climber.getSlowZoneRange() -1, climber.getSlowZoneRange() -1);
        climber.extend();
        verifyPower(
            climber.getDefaultPower()*climber.getSlowZoneFactor(),
            climber.getDefaultPower()*climber.getSlowZoneFactor());
    }

    private void verifyPower(double left, double right) {
        assertEquals(left, climber.leftMotor.get(), 0.001);
        assertEquals(right, climber.rightMotor.get(), 0.001);
    }

    private void setClimberPosition(double left, double right) {
        ((MockCANSparkMax)climber.leftMotor).setPosition(left);
        ((MockCANSparkMax)climber.rightMotor).setPosition(right);
    }

    private double getSafeRange() {
        return climber.getSlowZoneRange() + 10;
    }
}