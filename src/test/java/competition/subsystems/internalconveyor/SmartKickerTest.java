package competition.subsystems.internalconveyor;

import static org.junit.Assert.assertEquals;

import competition.BaseCompetitionTest;
import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.internalconveyor.commands.SmartKickerCommand;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;

public class SmartKickerTest extends BaseCompetitionTest {

    KickerSubsystem kicker;
    ShooterWheelSubsystem shooter;
    HoodSubsystem hood;

    SmartKickerCommand command;

    @Override
    public void setUp() {
        super.setUp();

        kicker = injector.getInstance(KickerSubsystem.class);
        shooter = injector.getInstance(ShooterWheelSubsystem.class);
        hood = injector.getInstance(HoodSubsystem.class);
        command = injector.getInstance(SmartKickerCommand.class);
    }

    private void verifyKickerPower(double power) {
        assertEquals(power, kicker.wheelMotor.getMotorOutputPercent(), 0.001);
    }

    private void setHoodPercent(double percent) {
        ((MockCANTalon)(hood.hoodMotor)).setPosition((int)(hood.getRange() * percent));
    }
}