package competition.subsystems;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import xbot.common.command.XScheduler;

@Singleton
public class SubsystemDefaultCommandMap {
    // For setting the default commands on subsystems

    @Inject
    public void setupDriveSubsystem(XScheduler scheduler, DriveSubsystem drive, ArcadeDriveCommand command) {
        //scheduler.setDefaultCommand(drive, command);
    }
}
