package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.armlifting.commands.CollectorArmLiftingCommand;
import competition.subsystems.armlifting.commands.FrontGrabbingBallsCommand;
import competition.subsystems.carousel.commands.TurnLeftCarouselCommand;
import competition.subsystems.carousel.commands.TurnRightCarouselCommand;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.hood.commands.ExtendHoodCommand;
import competition.subsystems.hood.commands.RetractHoodCommand;
import competition.subsystems.internalconveyor.commands.IntakeCommand;
import competition.subsystems.shooterwheel.commands.SpinningShooterWheelCommand;
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
        operatorInterface.driverGamepad.getifAvailable(1).whenPressed(arcade);
        operatorInterface.driverGamepad.getifAvailable(2).whenPressed(tank);
        operatorInterface.driverGamepad.getifAvailable(8).whenPressed(resetHeading);
    }

    @Inject
    public void setupBasicCommands(OperatorInterface operatorInterface, ExtendHoodCommand extendHood, 
    RetractHoodCommand retractHood, TurnLeftCarouselCommand carouselLeft, TurnRightCarouselCommand carouselRight,
    FrontGrabbingBallsCommand frontIntake, CollectorArmLiftingCommand liftArm, SpinningShooterWheelCommand spinShooterWheel)
    {
        operatorInterface.operatorGamepad.getifAvailable(1).whileHeld(carouselLeft);
        operatorInterface.operatorGamepad.getifAvailable(2).whileHeld(carouselRight);
        operatorInterface.operatorGamepad.getifAvailable(3).whileHeld(extendHood);
        operatorInterface.operatorGamepad.getifAvailable(4).whileHeld(retractHood);
        operatorInterface.operatorGamepad.getifAvailable(5).whileHeld(frontIntake);
        operatorInterface.operatorGamepad.getifAvailable(6).whileHeld(liftArm);
        operatorInterface.operatorGamepad.getifAvailable(7).whileHeld(spinShooterWheel);
        //TODO: add hang command
    }

    @Inject
    public void setupShootercommands(
        OperatorInterface operatorInterface,
        ShooterWheelSubsystem shooter,
        SpinningShooterWheelCommand singleWheel) {
        
        Command speedUp = new InstantCommand(() -> shooter.changeTargetSpeed(100));
        Command slowDown = new InstantCommand(() -> shooter.changeTargetSpeed(-100));
        Command stop = new RunCommand(() -> shooter.stop(), shooter);

        operatorInterface.operatorGamepad.getifAvailable(1).whenPressed(singleWheel);
        operatorInterface.operatorGamepad.getifAvailable(2).whenPressed(stop);
        operatorInterface.operatorGamepad.getifAvailable(5).whenPressed(speedUp);
        operatorInterface.operatorGamepad.getifAvailable(6).whenPressed(slowDown);
    }

}
