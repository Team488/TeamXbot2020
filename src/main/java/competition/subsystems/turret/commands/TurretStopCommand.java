package competition.subsystems.turret.commands;

import com.google.inject.Inject;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;

public class TurretStopCommand extends BaseCommand
{
    final TurretSubsystem turretSubsystem;

    @Inject
    public TurretStopCommand(TurretSubsystem tSubsystem) {
        this.turretSubsystem = tSubsystem;
        this.addRequirements(this.turretSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }
    
    @Override
    public void execute() {
        turretSubsystem.stop();
    }    
}