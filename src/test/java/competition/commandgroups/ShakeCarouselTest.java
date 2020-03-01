package competition.commandgroups;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.carousel.CarouselSubsystem;

public class ShakeCarouselTest extends BaseCompetitionTest {

    ShakeCarouselCommand shake;
    CarouselSubsystem carousel;

    @Override
    public void setUp() {
        super.setUp();
        shake = injector.getInstance(ShakeCarouselCommand.class);
        carousel = injector.getInstance(CarouselSubsystem.class);
    }

    @Test
    public void simpleTest() {
        verifyCarouselPower(0);
        shake.initialize();
        shake.execute();
        verifyCarouselPower(1);
        
        shake.execute();
        shake.execute();
        shake.execute();
        verifyCarouselPower(1);
        
        timer.advanceTimeInSecondsBy(1);
        shake.execute();
        shake.execute();
        shake.execute();
        verifyCarouselPower(-1);

    }

    private void verifyCarouselPower(double power) {
        assertEquals(power, carousel.carouselMotor.getMotorOutputPercent(), 0.001);
    }
}