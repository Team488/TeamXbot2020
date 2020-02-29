package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.commandgroups.PassTowardsTargetCommand;
import competition.commandgroups.PrepareToFireCommand;
import competition.commandgroups.ShutdownShootingCommand;
import competition.subsystems.carousel.commands.CarouselFiringModeCommand;
import competition.subsystems.climber.commands.DynamicClimbCommand;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.intake.commands.EjectCommand;
import competition.subsystems.intake.commands.IntakeCommand;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.shooterwheel.commands.BangBangCommand;
import competition.subsystems.shooterwheel.commands.ShooterWheelMaintainerCommand;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.turret.commands.PointTurretToFieldOrientedHeadingCommand;
import competition.subsystems.turret.commands.TurretRotateToVisionTargetCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.controls.sensors.AdvancedButton;
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
    public void setupCollectionCommands(OperatorInterface oi, IntakeCommand intake, EjectCommand eject) {
        oi.driverGamepad.getifAvailable(XboxButton.X).whileHeld(intake);
        oi.driverGamepad.getifAvailable(XboxButton.Y).whileHeld(eject);
    }

    @Inject
    public void setupTurretCommands(OperatorInterface oi, TurretSubsystem turret,
            TurretRotateToVisionTargetCommand rotateToVisionTarget,
            PointTurretToFieldOrientedHeadingCommand pointDownrange) {
        Command calibrate = new InstantCommand(() -> turret.calibrateTurret());
        Command oriented90 = new InstantCommand(() -> turret.setFieldOrientedGoalAngle(90));

        oi.operatorGamepad.getifAvailable(XboxButton.RightStick).whenPressed(calibrate);
        //oi.operatorGamepad.getifAvailable(XboxButton.Start).whenPressed(rotateToVisionTarget);
        oi.operatorGamepad.getifAvailable(XboxButton.X).whileHeld(pointDownrange);
        oi.manualOperatorGamepad.getifAvailable(XboxButton.RightStick).whenPressed(calibrate);
        oi.manualOperatorGamepad.getifAvailable(XboxButton.LeftStick).whenPressed(oriented90);
        oi.manualOperatorGamepad.getifAvailable(XboxButton.Start).whileHeld(rotateToVisionTarget);
    }

    @Inject
    public void setupShooterCommands(OperatorInterface operatorInterface, ShooterWheelSubsystem shooter,
            ShooterWheelMaintainerCommand singleWheel, BangBangCommand bangBang) {

        Command speedUp = new InstantCommand(() -> shooter.changeTargetRPM(500), shooter.getSetpointLock());
        Command slowDown = new InstantCommand(() -> shooter.changeTargetRPM(-500), shooter.getSetpointLock());
        Command stop = new RunCommand(() -> shooter.stop(), shooter);

        operatorInterface.operatorGamepad.getifAvailable(XboxButton.A).whenPressed(singleWheel);
        operatorInterface.operatorGamepad.getifAvailable(XboxButton.B).whenPressed(stop);
        operatorInterface.operatorGamepad.getifAvailable(XboxButton.LeftBumper).whenPressed(slowDown);
        operatorInterface.operatorGamepad.getifAvailable(XboxButton.RightBumper).whenPressed(speedUp);
        operatorInterface.manualOperatorGamepad.getifAvailable(XboxButton.RightBumper).whenPressed(speedUp);
        operatorInterface.manualOperatorGamepad.getifAvailable(XboxButton.LeftBumper).whenPressed(slowDown);
    }

    @Inject
    public void setupOperatorCommandGroups(OperatorInterface oi, PassTowardsTargetCommand passCommand,
    PrepareToFireCommand prepareToFire, CarouselFiringModeCommand carouselFiringMode, ShutdownShootingCommand stopShooting) {
        oi.operatorGamepad.getifAvailable(XboxButton.Y).whenPressed(passCommand, false);

        var fire = new SequentialCommandGroup(prepareToFire, carouselFiringMode);
        AdvancedButton fireButton = oi.operatorGamepad.getifAvailable(XboxButton.Start);
        fireButton.whenPressed(fire);
        fireButton.whenReleased(stopShooting);
    }

    @Inject
    public void setupClimbCommands(OperatorInterface oi, DynamicClimbCommand dynamicClimb) {
        oi.operatorGamepad.getifAvailable(XboxButton.Back).whileHeld(dynamicClimb);
    }

    @Inject
    public void setUpHoodCommands(OperatorInterface oi, HoodSubsystem hood){
        oi.operatorGamepad.getifAvailable(XboxButton.LeftStick).whenPressed(new InstantCommand(hood::calibrateHood));

        var hoodForward = new InstantCommand(() -> hood.changeTargetPercent(10), hood.getSetpointLock());
        var hoodBack = new InstantCommand(() -> hood.changeTargetPercent(10), hood.getSetpointLock());

        oi.operatorGamepad.getPovIfAvailable(0).whenPressed(hoodForward);
        oi.operatorGamepad.getPovIfAvailable(180).whenPressed(hoodBack);
    }
}
