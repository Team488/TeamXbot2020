package competition.commandgroups;

import com.google.inject.Inject;

import competition.subsystems.carousel.commands.CarouselFiringModeCommand;
import competition.subsystems.internalconveyor.commands.SmartKickerCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class ShootCommand extends ParallelCommandGroup {

    private CarouselFiringModeCommand spinCarousel;

    @Inject
    public ShootCommand(SmartKickerCommand smartKicker, CarouselFiringModeCommand smartCarousel) {
        this.spinCarousel = smartCarousel;

        this.addCommands(smartKicker, smartCarousel);
    }

    public void setStreamFire() {
        spinCarousel.setStreamFire(true);
    }
}