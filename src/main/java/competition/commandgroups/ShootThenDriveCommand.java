package competition.commandgroups;

import com.google.inject.Inject;

import competition.subsystems.carousel.commands.TurnRightCarouselCommand;
import competition.subsystems.internalconveyor.commands.LiftCellsCommand;
import competition.subsystems.shooterwheel.commands.ShooterWheelSetPassPowerCommand;
import competition.subsystems.shooterwheel.commands.ShooterWheelWaitForGoalCommand;
import competition.subsystems.turret.commands.PointTurretToFieldOrientedHeadingCommand;
import competition.subsystems.turret.commands.TurretWaitForRotationToGoalCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ShootThenDriveCommand extends ParallelCommandGroup {

    @Inject
    public ShootThenDriveCommand(PointTurretToFieldOrientedHeadingCommand rotateTurretCommand,
        ShooterWheelSetPassPowerCommand shooterWheelPowerCommand,
        TurretWaitForRotationToGoalCommand waitForRotateTurretCommand,
        ShooterWheelWaitForGoalCommand waitForShooterWheelSpeedCommand,
        TurnRightCarouselCommand carouselCommand,
        LiftCellsCommand kickerCommand) { //parameters

        this.addCommands( 
            rotateTurretCommand, //rotates to face the target
            shooterWheelPowerCommand,//sets power based on location of target
            // hood
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    waitForRotateTurretCommand,//wait to finish
                    waitForShooterWheelSpeedCommand //wait to finish
                    // wait for hood
                ),
                new ParallelCommandGroup(
                    carouselCommand,
                    kickerCommand
                )
            )
        );
    }
}
//command to shoot for n seconds
//command to drive y inches back
//command to wait for shoot command
//command to wait for drive command