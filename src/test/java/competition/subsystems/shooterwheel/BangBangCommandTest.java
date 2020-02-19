package competition.subsystems.shooterwheel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.shooterwheel.commands.BangBangCommand;

public class BangBangCommandTest extends BaseCompetitionTest {
    @Test
    public void testFullPower(){
        ShooterWheelSubsystem shooterWheelSubsystem = this.injector.getInstance(ShooterWheelSubsystem.class);
        BangBangCommand command = this.injector.getInstance(BangBangCommand.class);
        shooterWheelSubsystem.setTargetRPM(400);
        command.initialize();
        command.execute();

        assertEquals(1, shooterWheelSubsystem.getPower(), 0.001);
    }
    @Test
    public void testZeroPower(){
        ShooterWheelSubsystem shooterWheelSubsystem = this.injector.getInstance(ShooterWheelSubsystem.class);
        BangBangCommand command = this.injector.getInstance(BangBangCommand.class);
        shooterWheelSubsystem.setTargetRPM(-100);
        command.initialize();
        command.execute();

        assertEquals(0, shooterWheelSubsystem.getPower(), 0.001);
    }
}