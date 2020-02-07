package competition.subsystems.carousel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class CarouselSubsystemTest extends BaseCompetitionTest {
    
    @Test
    public void testTurnLeft() {
        CarouselSubsystem carousel = this.injector.getInstance(CarouselSubsystem.class);
        carousel.turnLeft();

        assertEquals(1, carousel.carouselMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testTurnRight() {
        CarouselSubsystem carousel = this.injector.getInstance(CarouselSubsystem.class);
        carousel.turnRight();

        assertEquals(-1, carousel.carouselMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testStop() {
        CarouselSubsystem carousel = this.injector.getInstance(CarouselSubsystem.class);
        carousel.turnLeft();
        if(carousel.carouselMotor.getMotorOutputPercent() > 0){
            carousel.stop();
        }
        
        assertEquals(0, carousel.carouselMotor.getMotorOutputPercent(), 0.001);
    }
}