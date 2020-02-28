package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;

public class DriveBackwardCommand extends BaseCommand {

    final DriveSubsystem driveSubsystem;
    final OperatorInterface oi;
    final double power;

    @Inject
    public DriveBackwardCommand(OperatorInterface oi, DriveSubsystem driveSubsystem, double power) {
        this.oi = oi;
        this.driveSubsystem = driveSubsystem;
        this.power = power;
        this.addRequirements(driveSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        driveSubsystem.driveBackward(power);
    }
}
