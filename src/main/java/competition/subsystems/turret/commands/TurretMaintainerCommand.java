package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;

public class TurretMaintainerCommand extends BaseCommand {

    final TurretSubsystem turretSubsystem;
    final DriveSubsystem driveSubsystem;

    @Inject
    public TurretMaintainerCommand(TurretSubsystem tSubsystem, DriveSubsystem driveSubsystem) {
        this.turretSubsystem = tSubsystem;
        this.driveSubsystem = driveSubsystem;
        this.addRequirements(this.turretSubsystem);
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void initialize() {
        log.info("Initializing");

    }

}