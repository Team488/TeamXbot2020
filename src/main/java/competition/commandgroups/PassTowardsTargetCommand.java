package competition.commandgroups;

import com.google.inject.Inject;

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
        ShooterWheelWaitForGoalCommand waitForShooterWheelSpeedCommand) {

        this.addCommands(
            rotateTurretCommand,
            shooterWheelPowerCommand,
            // carousel
            // hood
            new SequentialCommandGroup(
                new ParallelCommandGroup(
                    waitForRotateTurretCommand,
                    waitForShooterWheelSpeedCommand
                    // wait for hood
                )
                // kicker
            )
        );
    }

}