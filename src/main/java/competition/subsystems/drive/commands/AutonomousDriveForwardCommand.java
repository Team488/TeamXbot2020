package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;

public class AutonomousDriveCommand extends BaseCommand {

    DriveSubsystem drive;

    private double leftPower;
    private double rightPower;

    @Inject
    public AutonomousDriveCommand(DriveSubsystem drive) {
        this.drive = drive;
    }

    public void setDrivePower(double left, double right) {
        this.leftPower = left;
        this.rightPower = right;
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        drive.tankDrive(leftPower, rightPower);
    }
}