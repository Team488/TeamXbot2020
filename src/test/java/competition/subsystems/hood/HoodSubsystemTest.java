package competition.subsystems.hood;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;

public class HoodSubsystemTest extends BaseCompetitionTest {
    
    HoodSubsystem hood;

    @Override
    public void setUp() {
        super.setUp();
        hood = injector.getInstance(HoodSubsystem.class);
    }

    @Test
    public void testExtendHood() {
        hood.extend();

        assertEquals(1, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testRetractHood(){
        hood.retract();
        
        assertEquals(-1, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testIsFullyExtended(){
        hood.currentPercentExtendedProp.set(1); //hood is set to 1% aka max
        hood.setIsCalibrated(true);
        hood.setPower(1); //tries to extend past max
        
        assertEquals(0, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testIsFullyExtracted(){
        hood.currentPercentExtendedProp.set(0); //hood is set to 0% aka min
        hood.setIsCalibrated(true);
        hood.setPower(-1); //tries to retract it past min
        
        assertEquals(0, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }
    
    @Test
    public void testAngleExtended(){
        changeTicks(0);
        hood.calibrateHood();
        changeTicks(500);
        assertEquals(.5, hood.getPercentExtended(), 0.001);
    }

    @Test
    public void testAngleExtended2(){
        changeTicks(500);
        hood.calibrateHood();
        changeTicks(1000);
        assertEquals(.5, hood.getPercentExtended(), 0.001);
    }

    @Test
    public void testAngleExtended3(){
        changeTicks(238);
        hood.calibrateHood();
        changeTicks(496);
        assertEquals(.258, hood.getPercentExtended(), 0.001);
    }

    @Test
    public void testRetractForCalibation(){
        hood.retractForCalibration();

        assertEquals(-0.05, hood.hoodMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testCalibrationTimeout(){
        assertFalse(hood.isCalibrateInProgress());
        assertFalse(hood.isCalibrated());

        hood.setCalibrationStartTime();
        assertTrue(hood.isCalibrateInProgress());
        assertFalse(hood.isCalibrateTimedOut());

        timer.advanceTimeInSecondsBy(10);
        assertTrue(hood.isCalibrateTimedOut());
    }

    private void changeTicks(int ticks) {
        ((MockCANTalon)(hood.hoodMotor)).setPosition(ticks);
    }
}
