package competition.subsystems;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.carousel.CarouselSubsystem;
import competition.subsystems.carousel.commands.StopCarouselCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import xbot.common.command.XScheduler;

@Singleton
public class SubsystemDefaultCommandMap {
    // For setting the default commands on subsystems

    @Inject
    public void setupDriveSubsystem(XScheduler scheduler, DriveSubsystem drive, ArcadeDriveCommand command) {
        scheduler.setDefaultCommand(drive, command);
    }

    @Inject
    public void setupCarouselSubsystem(XScheduler scheduler, CarouselSubsystem carousel, StopCarouselCommand command) {
        scheduler.setDefaultCommand(carousel, command);
    }
}
