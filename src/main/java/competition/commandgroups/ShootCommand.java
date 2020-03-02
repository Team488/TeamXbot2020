package competition.commandgroups;

import com.google.inject.Inject;

import competition.subsystems.carousel.commands.CarouselFiringModeCommand;
import competition.subsystems.internalconveyor.commands.SmartKickerCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootCommand extends SequentialCommandGroup {

    @Inject
    public ShootCommand(SmartKickerCommand smartKicker, CarouselFiringModeCommand smartCarousel) {
        this.addCommands(smartKicker, smartCarousel);
    }
}