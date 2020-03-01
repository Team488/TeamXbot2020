package competition.subsystems.carousel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.carousel.commands.CarouselManagerCommand;

public class CarouselManagerTest extends BaseCompetitionTest {

    CarouselSubsystem carousel;
    CarouselManagerCommand command;

    @Override
    public void setUp() {
        super.setUp();

        this.carousel = injector.getInstance(CarouselSubsystem.class);
        this.command = injector.getInstance(CarouselManagerCommand.class);
    }

    private void setPower(double power) {
        command.setPowerSupplier(() -> power);
    }

    private void verifyPower(double expectedPower) {
        assertEquals(expectedPower, carousel.carouselMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testSimpleOperation() {
        verifyPower(0);

        setPower(1);
        command.initialize();
        command.execute();
        verifyPower(1);

        timer.advanceTimeInSecondsBy(command.getForwardTime() * 0.9);
        command.execute();
        verifyPower(1);

        timer.advanceTimeInSecondsBy(command.getForwardTime() * 0.101);
        command.execute();
        verifyPower(-1);

        timer.advanceTimeInSecondsBy(command.getReverseTime() * 0.5);
        command.execute();
        verifyPower(-1);

        timer.advanceTimeInSecondsBy(command.getReverseTime() * 0.6);
        command.execute();
        verifyPower(1);
    }
}