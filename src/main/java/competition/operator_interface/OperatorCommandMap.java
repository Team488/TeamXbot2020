package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

import competition.autonomous.BasicAutonomousCommand;
import competition.autonomous.DoNothingInAutonomousCommand;
import competition.autonomous.OnlyDriveAutonomousCommand;
import competition.commandgroups.ShakeCarouselCommand;
import competition.commandgroups.ShootCommand;
import competition.commandgroups.TrenchSafetyCommand;
import competition.multisubsystemcommands.SetWheelAndHoodGoalsCommand;
import competition.multisubsystemcommands.SetWheelAndHoodGoalsCommand.FieldPosition;
import competition.subsystems.arm.commands.LowerArmCommand;
import competition.subsystems.arm.commands.RaiseArmCommand;
import competition.subsystems.climber.commands.DynamicClimbCommand;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.turret.commands.PointTurretToFieldOrientedHeadingCommand;
import competition.subsystems.turret.commands.ReCenterTurretCommand;
import competition.subsystems.turret.commands.TurretRotateToVisionTargetCommand;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import xbot.common.command.NamedInstantCommand;
import xbot.common.controls.sensors.AdvancedButton;
import xbot.common.controls.sensors.XXboxController.XboxButton;
import xbot.common.subsystems.autonomous.SetAutonomousCommand;
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
    public void setupCollectionCommands(OperatorInterface oi) {
    }

    @Inject
    public void setupTurretCommands(OperatorInterface oi, TurretSubsystem turret,
            TurretRotateToVisionTargetCommand rotateToVisionTarget,
            PointTurretToFieldOrientedHeadingCommand pointDownrange,
            ReCenterTurretCommand recenter) {
        Command calibrate = new NamedInstantCommand("TurretCalibrateInstantCommand", () -> turret.calibrateTurret());
        Command oriented90 = new NamedInstantCommand("TurretOriented90InstantCommand", () -> turret.setFieldOrientedGoalAngle(90));

        // oi.operatorGamepad.getifAvailable(XboxButton.Start).whenPressed(rotateToVisionTarget);
        // oi.operatorGamepad.getifAvailable(XboxButton.X).whileHeld(pointDownrange);
        oi.operatorGamepad.getifAvailable(XboxButton.RightStick).whenPressed(recenter);
        oi.manualOperatorGamepad.getifAvailable(XboxButton.RightStick).whenPressed(calibrate);
        oi.manualOperatorGamepad.getifAvailable(XboxButton.Start).whileHeld(rotateToVisionTarget);
    }

    @Inject
    public void setupShooterCommands(OperatorInterface oi, ShooterWheelSubsystem shooter, 
    Provider<SetWheelAndHoodGoalsCommand> setGoalsProvider, ShootCommand shoot) {

        // MANUAL OVERRIDES
        Command speedUp = new NamedInstantCommand("ShooterSpeedUp500RPMInstantCommand", () -> shooter.changeTargetRPM(500), shooter.getSetpointLock());
        Command slowDown = new NamedInstantCommand("ShooterSlowDown500RPMInstantCommand", () -> shooter.changeTargetRPM(-500), shooter.getSetpointLock());
        Command stopShooter = new RunCommand(() -> shooter.stop(), shooter);

        oi.manualOperatorGamepad.getifAvailable(XboxButton.RightBumper).whenPressed(speedUp);
        oi.manualOperatorGamepad.getifAvailable(XboxButton.LeftBumper).whenPressed(slowDown);
        oi.manualOperatorGamepad.getifAvailable(XboxButton.B).whenPressed(stopShooter);

        // REAL COMMANDS
        
        Command increaseTrim = new NamedInstantCommand("ShooterIncreaseTrim100RPMInstantCommand", () -> shooter.changeTrimRPM(100));
        Command decreaseTrim = new NamedInstantCommand("ShooterDecreaseTrim100RPMInstantCommand", () -> shooter.changeTrimRPM(-100));

        SetWheelAndHoodGoalsCommand initiationLob = setGoalsProvider.get();
        SetWheelAndHoodGoalsCommand intitationLaser = setGoalsProvider.get();
        SetWheelAndHoodGoalsCommand trenchNearGoal = setGoalsProvider.get();
        SetWheelAndHoodGoalsCommand pass = setGoalsProvider.get();
        SetWheelAndHoodGoalsCommand safe = setGoalsProvider.get();

        initiationLob.setGoals(FieldPosition.InitiationCloseToGoal);
        intitationLaser.setGoals(FieldPosition.InitiationLaser);
        trenchNearGoal.setGoals(FieldPosition.TrenchCloseToGoal);
        pass.setGoals(FieldPosition.InitiationFarFromGoal);
        safe.setGoals(FieldPosition.SafeMode);

        bindGoalsAndSafety(oi.operatorGamepad.getifAvailable(XboxButton.A), initiationLob, safe);
        bindGoalsAndSafety(oi.operatorGamepad.getifAvailable(XboxButton.B), intitationLaser, safe);
        bindGoalsAndSafety(oi.operatorGamepad.getifAvailable(XboxButton.X), trenchNearGoal, safe);
        bindGoalsAndSafety(oi.operatorGamepad.getifAvailable(XboxButton.Y), pass, safe);

        oi.operatorGamepad.getifAvailable(XboxButton.RightTrigger).whileHeld(shoot);

        oi.operatorGamepad.getifAvailable(XboxButton.LeftBumper).whenPressed(increaseTrim);
        oi.operatorGamepad.getifAvailable(XboxButton.RightBumper).whenPressed(decreaseTrim);
    }

    @Inject
    public void setupCarouselCommands(OperatorInterface oi, ShakeCarouselCommand shake) {
        oi.operatorGamepad.getifAvailable(XboxButton.LeftStick).whileHeld(shake);
    }

    private void bindGoalsAndSafety(AdvancedButton button, Command heldCommand, Command releaseCommand) {
        button.whileHeld(heldCommand);
        button.whenReleased(releaseCommand);
    }

    @Inject
    public void setupClimbCommands(OperatorInterface oi, DynamicClimbCommand dynamicClimb) {
        oi.operatorGamepad.getifAvailable(XboxButton.Back).whileHeld(dynamicClimb);
    }

    @Inject
    public void setUpHoodCommands(OperatorInterface oi, HoodSubsystem hood) {
        oi.manualOperatorGamepad.getifAvailable(XboxButton.LeftStick)
                .whenPressed(new NamedInstantCommand("HoodCalibrate", (hood::calibrateHood)));
        var hoodForward = new NamedInstantCommand("HoodForwardInstantCommand", () -> hood.changeTargetPercent(0.05), hood.getSetpointLock());
        var hoodBack = new NamedInstantCommand("HoodBackInstantCommand", () -> hood.changeTargetPercent(-0.05), hood.getSetpointLock());

        oi.manualOperatorGamepad.getPovIfAvailable(0).whenPressed(hoodForward);
        oi.manualOperatorGamepad.getPovIfAvailable(180).whenPressed(hoodBack);
    }

    @Inject
    public void setupArmCommands(OperatorInterface oi, RaiseArmCommand raiseArm, LowerArmCommand lowerArm, TrenchSafetyCommand trenchSafety) {
        oi.driverGamepad.getifAvailable(XboxButton.LeftBumper).whenPressed(raiseArm);
        oi.driverGamepad.getifAvailable(XboxButton.RightBumper).whenPressed(lowerArm);

        oi.driverGamepad.getifAvailable(XboxButton.X).whenPressed(trenchSafety);
    }

    @Inject
    public void setupAutonomousCommands(Provider<SetAutonomousCommand> setAuto, BasicAutonomousCommand basicAuto,
    DoNothingInAutonomousCommand doNothing, OnlyDriveAutonomousCommand onlyDrive)  {
        var setBasicAuto = setAuto.get();
        var setDoNothing = setAuto.get();
        var setOnlyDrive = setAuto.get();

        setBasicAuto.setAutoCommand(basicAuto);
        setDoNothing.setAutoCommand(doNothing);
        setOnlyDrive.setAutoCommand(onlyDrive);

        setBasicAuto.includeOnSmartDashboard("Auto Programs/Shoot 3 Then Drive");
        setDoNothing.includeOnSmartDashboard("Auto Programs/Do Nothing In Auto");
        setOnlyDrive.includeOnSmartDashboard("Auto Programs/Only Drive In Auto");
    }
}
