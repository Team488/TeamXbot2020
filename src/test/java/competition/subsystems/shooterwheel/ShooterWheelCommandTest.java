package competition.subsystems.shooterwheel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.shooterwheel.commands.SpinningShooterWheelCommand;

public class ShooterWheelCommandTest extends BaseCompetitionTest {
    
    // @Test
    // public void testSpin() {
    //     ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
    //     SpinningShooterWheelCommand command = this.injector.getInstance(SpinningShooterWheelCommand.class);
    //     command.initialize();
    //     shooterWheel.changeTargetSpeed(200);
    //     command.execute();
    
    //     assertEquals(shooterWheel.getTargetSpeed(), shooterWheel.leader.getVelocity(), 0.0001);
    // }
}