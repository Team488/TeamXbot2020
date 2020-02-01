package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;

public class turretRotationCommand extends BaseCommand
{
    final OperatorInterface oi;
    final TurretSubsystem turretSubsystem;

    @Inject
    public turretRotationCommand(OperatorInterface oi , TurretSubsystem tSubsystem) {
        this.oi = oi;
        this.turretSubsystem = tSubsystem;

        this.addRequirements(this.turretSubsystem);

    }

    @Override
    public void execute() {
        turretSubsystem.setPower(0);

    }

    @Override
    public void initialize() {
        log.info("Initializing");

    }
    
}