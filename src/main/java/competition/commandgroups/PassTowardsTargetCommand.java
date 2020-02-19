package competition.commandgroups;

import com.google.inject.Inject;

import competition.subsystems.turret.commands.PointTurretToFieldOrientedHeadingCommand;
import competition.subsystems.turret.commands.TurretWaitForRotationToGoalCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class PassTowardsTargetCommand extends SequentialCommandGroup {

    @Inject
    public PassTowardsTargetCommand(PointTurretToFieldOrientedHeadingCommand rotateTurretCommand,
        TurretWaitForRotationToGoalCommand waitForRotateTurretCommand) {

        this.addCommands(
            rotateTurretCommand,
            waitForRotateTurretCommand
            // shooter wheel
            // adjust hood
            // kicker
            // carousel
        );
    }

}