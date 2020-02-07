package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.armlifting.commands.CollectorArmLiftingCommand;
import competition.subsystems.armlifting.commands.FrontGrabbingBallsCommand;
import competition.subsystems.carousel.commands.StopCarouselCommand;
import competition.subsystems.carousel.commands.TurnLeftCarouselCommand;
import competition.subsystems.carousel.commands.TurnRightCarouselCommand;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.drive.commands.TankDriveWithJoysticksCommand;
import competition.subsystems.hood.commands.ExtendHoodCommand;
import competition.subsystems.hood.commands.RetractHoodCommand;
import competition.subsystems.internalconveyor.commands.IntakeCommand;
import competition.subsystems.shooterwheel.commands.SpinningShooterWheelCommand;
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
    public void setupBasicCommands(OperatorInterface operatorInterface, ExtendHoodCommand extendHood, RetractHoodCommand retractHood,
    StopCarouselCommand stopCarousel, TurnLeftCarouselCommand carouselLeft, TurnRightCarouselCommand carouselRight,
    FrontGrabbingBallsCommand frontIntake, CollectorArmLiftingCommand liftArm, SpinningShooterWheelCommand spinShooterWheel)
    {
        operatorInterface.operatorGamepad.getifAvailable(1).whenPressed(carouselLeft);
        operatorInterface.operatorGamepad.getifAvailable(2).whenPressed(carouselRight);
        operatorInterface.operatorGamepad.getifAvailable(3).whenPressed(stopCarousel);
        operatorInterface.operatorGamepad.getifAvailable(4).whenPressed(extendHood);
        operatorInterface.operatorGamepad.getifAvailable(5).whenPressed(retractHood);
        operatorInterface.operatorGamepad.getifAvailable(6).whenPressed(frontIntake);
        operatorInterface.operatorGamepad.getifAvailable(7).whenPressed(liftArm);
        operatorInterface.operatorGamepad.getifAvailable(8).whenPressed(spinShooterWheel);
        //TODO: add hang command
    }
}
