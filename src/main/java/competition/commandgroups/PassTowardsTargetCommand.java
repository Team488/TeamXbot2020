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

public class PassTowardsTargetCommand extends ParallelCommandGroup {

    @Inject
    public PassTowardsTargetCommand(PointTurretToFieldOrientedHeadingCommand rotateTurretCommand,
        ShooterWheelSetPassPowerCommand shooterWheelPowerCommand,
        TurretWaitForRotationToGoalCommand waitForRotateTurretCommand,
        ShooterWheelWaitForGoalCommand waitForShooterWheelSpeedCommand,
        TurnRightCarouselCommand carouselCommand,
        LiftCellsCommand kickerCommand) {

        this.addCommands(
            rotateTurretCommand,
            shooterWheelPowerCommand,
            // hood
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    waitForRotateTurretCommand,
                    waitForShooterWheelSpeedCommand
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