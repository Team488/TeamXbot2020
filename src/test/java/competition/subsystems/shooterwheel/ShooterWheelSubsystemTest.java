package competition.subsystems.shooterwheel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class ShooterWheelSubsystemTest extends BaseCompetitionTest {
    
    @Test
    public void testSpin() {
        ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
        shooterWheel.spin();

        assertEquals(1, shooterWheel.shooterWheelMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testStop() {
        ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
        shooterWheel.stop();

        assertEquals(0, shooterWheel.shooterWheelMotor.getMotorOutputPercent(), 0.001);
    }
    
}