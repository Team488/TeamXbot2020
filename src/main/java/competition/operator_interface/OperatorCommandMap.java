package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.shooterwheel.commands.SpinningShooterWheelCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
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
    public void setupShootercommands(
        OperatorInterface operatorInterface,
        ShooterWheelSubsystem shooter,
        SpinningShooterWheelCommand singleWheel) {
            Command speedUp = new InstantCommand(() -> shooter.changeTargetSpeed(100));
            Command slowDown = new InstantCommand(() -> shooter.changeTargetSpeed(-100));
            Command stop = new RunCommand(() -> shooter.stop(), shooter);

            operatorInterface.gamepad2.getifAvailable(1).whenPressed(singleWheel);
            operatorInterface.gamepad2.getifAvailable(2).whenPressed(stop);
            operatorInterface.gamepad2.getifAvailable(5).whenPressed(speedUp);
            operatorInterface.gamepad2.getifAvailable(6).whenPressed(slowDown);
    }
}
