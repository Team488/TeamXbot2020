package competition.commandgroups;

import com.google.inject.Inject;

import competition.subsystems.arm.commands.LowerArmCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;

public class TrenchSafetyCommand extends ParallelCommandGroup {

    @Inject
    public TrenchSafetyCommand(LowerArmCommand lowerArm,
            FullyRetractHoodCommand retractHoodCommand,
            ShutdownShootingCommand stopShooting) {
        this.addCommands(lowerArm, retractHoodCommand, stopShooting);
    }

}