import com.google.inject.Inject;

import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;

public class TurretMaintainerCommand extends BaseCommand {

    final TurretSubsystem turretSubsystem;

    @Inject
    public TurretMaintainerCommand(TurretSubsystem tSubsystem) {
        this.turretSubsystem = tSubsystem;
        this.addRequirements(this.turretSubsystem);
    }

    @Override
    public void execute() {
        turret
    }

    @Override
    public void initialize() {
        log.info("Initializing");

    }

}