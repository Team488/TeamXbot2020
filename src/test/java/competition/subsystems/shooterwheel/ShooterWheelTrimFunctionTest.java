package competition.subsystems.shooterwheel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class ShooterWheelTrimFunctionTest extends BaseCompetitionTest {

    @Test
    public void testTrimWithZeroRPM() {
        // Tests to see if Trim will not apply when currentRPM is at 0.
        ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
        
        shooterWheel.setTargetRPM(0);
        shooterWheel.increaseTargetTrimRPM(100);
        assertEquals(0, shooterWheel.getTargetRPM(), 0.01);
    }

    @Test
    public void testTrimWith100RPM() {
        // Tests to see if 100RPM of Trim will apply when currentRPM is at 100.
        ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
        
        shooterWheel.setTargetRPM(100);
        shooterWheel.increaseTargetTrimRPM(100);
        assertEquals(200, shooterWheel.getTargetRPM(), 0.01);
    }

    @Test
    public void testResetTrim() {
        // Tests to see if resetTrim will work.
        ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
        
        shooterWheel.setTargetRPM(100);
        shooterWheel.increaseTargetTrimRPM(100);
        assertEquals(200, shooterWheel.getTargetRPM(), 0.01);
        shooterWheel.resetTrimRPM();
        assertEquals(100, shooterWheel.getTargetRPM(), 0.01);
    }
}