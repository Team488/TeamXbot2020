package competition.commandgroups;

import com.google.inject.Inject;

import competition.subsystems.carousel.CarouselSubsystem;
import competition.subsystems.internalconveyor.KickerSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;

public class ShutdownShootingCommand extends SequentialCommandGroup {

    @Inject
    public ShutdownShootingCommand(CarouselSubsystem carousel, KickerSubsystem kicker) {
        DelayViaSupplierCommand waitCommand = new DelayViaSupplierCommand(() -> 1.0);

        this.addCommands(
            new InstantCommand(carousel::stop, carousel),
            waitCommand,
            new InstantCommand(kicker::stop, kicker));
    }
}