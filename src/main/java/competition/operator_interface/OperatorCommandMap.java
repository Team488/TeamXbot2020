package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;

/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {
    
    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(
            OperatorInterface operatorInterface,
            SetRobotHeadingCommand resetHeading,
            ArcadeDriveCommand arcade,
            TankDriveWithJoysticksCommand tank)
    {
        resetHeading.setHeadingToApply(90);
        operatorInterface.gamepad.getifAvailable(1).whenPressed(arcade);
        operatorInterface.gamepad.getifAvailable(2).whenPressed(tank);
        operatorInterface.gamepad.getifAvailable(8).whenPressed(resetHeading);
    }
}
