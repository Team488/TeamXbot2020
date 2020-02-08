package competition.subsystems.shooterwheel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.shooterwheel.commands.SpinningShooterWheelCommand;

public class ShooterWheelCommandTest extends BaseCompetitionTest {
    
    @Test
    public void testSpin() {
        ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
        SpinningShooterWheelCommand command = this.injector.getInstance(SpinningShooterWheelCommand.class);
        command.initialize();
        command.execute();
    
        assertEquals(shooterWheel.getTargetSpeed(), shooterWheel.neoMasterMotor.getVelocity(), 150);
    }

    @Test
    public void testStop() {
        ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
        shooterWheel.stop();

        assertEquals(0, shooterWheel.neoMasterMotor.get(), 0.001);
    }
    
}