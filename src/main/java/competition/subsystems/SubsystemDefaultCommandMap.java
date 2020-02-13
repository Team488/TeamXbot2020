package competition.subsystems;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.armlifting.CollectorArmLiftingSubsystem;
import competition.subsystems.armlifting.FrontCollectingSubsystem;
import competition.subsystems.armlifting.commands.StopArmCommand;
import competition.subsystems.armlifting.commands.StopFrontIntakeCommand;
import competition.subsystems.carousel.CarouselSubsystem;
import competition.subsystems.carousel.commands.StopCarouselCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.hood.commands.StopHoodCommand;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.shooterwheel.commands.StopShooterWheelCommand;
import xbot.common.command.XScheduler;

@Singleton
public class SubsystemDefaultCommandMap {
    // For setting the default commands on subsystems
    @Inject
    public void setupDriveSubsystem(XScheduler scheduler, DriveSubsystem drive, ArcadeDriveCommand command) {
        //scheduler.setDefaultCommand(drive, command);
    }

    @Inject
    public void setupCarouselSubsystem(XScheduler scheduler, CarouselSubsystem carousel, StopCarouselCommand command) {
        scheduler.setDefaultCommand(carousel, command);
    }

    @Inject
    public void setupHoodSubsystem(XScheduler scheduler, HoodSubsystem hood, StopHoodCommand command) {
        scheduler.setDefaultCommand(hood, command);
    }

    @Inject
    public void setupCollectorArmLiftingSubsystem(XScheduler scheduler, CollectorArmLiftingSubsystem arm, StopArmCommand command) {
        scheduler.setDefaultCommand(arm, command);
    }

    @Inject
    public void setupFrontCollectingSubsystem(XScheduler scheduler, FrontCollectingSubsystem frontIntake, StopFrontIntakeCommand command) {
        scheduler.setDefaultCommand(frontIntake, command);
    }

    @Inject
    public void setupShooterWheelSubsystem(XScheduler scheduler, ShooterWheelSubsystem shooterWheel, StopShooterWheelCommand command) {
        scheduler.setDefaultCommand(shooterWheel, command);
    }
}
