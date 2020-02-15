package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.shooterwheel.commands.BangBangCommand;
import competition.subsystems.shooterwheel.commands.SpinningShooterWheelCommand;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.turret.commands.PointTurretToFieldOrientedHeadingCommand;
import competition.subsystems.turret.commands.TurretRotateToVisionTargetCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import xbot.common.controls.sensors.XXboxController.XboxButton;
import xbot.common.subsystems.pose.commands.SetRobotHeadingCommand;

/**
 * Maps operator interface buttons to commands
 */
@Singleton
public class OperatorCommandMap {

    // Example for setting up a command to fire when a button is pressed:
    @Inject
    public void setupMyCommands(OperatorInterface operatorInterface, SetRobotHeadingCommand resetHeading,
            ArcadeDriveCommand arcade, TankDriveWithJoysticksCommand tank) {
        resetHeading.setHeadingToApply(90);
        operatorInterface.driverGamepad.getifAvailable(XboxButton.A).whenPressed(arcade);
        operatorInterface.driverGamepad.getifAvailable(XboxButton.B).whenPressed(tank);
        operatorInterface.driverGamepad.getifAvailable(XboxButton.Start).whenPressed(resetHeading);
    }

    @Inject
    public void setupTurretCommands(OperatorInterface oi, TurretSubsystem turret,
            TurretRotateToVisionTargetCommand rotateToVisionTarget,
            PointTurretToFieldOrientedHeadingCommand pointDownrange) {
        Command calibrate = new InstantCommand(() -> turret.calibrateTurret());

        oi.operatorGamepad.getifAvailable(XboxButton.RightStick).whenPressed(calibrate);
        oi.operatorGamepad.getifAvailable(XboxButton.Start).whenPressed(rotateToVisionTarget);
        oi.operatorGamepad.getifAvailable(XboxButton.X).whileHeld(pointDownrange);
    }

    @Inject
    public void setupShootercommands(OperatorInterface operatorInterface, ShooterWheelSubsystem shooter,
            SpinningShooterWheelCommand singleWheel, BangBangCommand bangBang) {

        Command speedUp = new InstantCommand(() -> shooter.changeTargetRPM(500));
        Command slowDown = new InstantCommand(() -> shooter.changeTargetRPM(-500));
        Command stop = new RunCommand(() -> shooter.stop(), shooter);

        operatorInterface.operatorGamepad.getifAvailable(XboxButton.A).whenPressed(singleWheel);
        operatorInterface.operatorGamepad.getifAvailable(XboxButton.B).whenPressed(stop);
        operatorInterface.operatorGamepad.getifAvailable(XboxButton.LeftBumper).whenPressed(slowDown);
        operatorInterface.operatorGamepad.getifAvailable(XboxButton.RightBumper).whenPressed(speedUp);
    }

}
