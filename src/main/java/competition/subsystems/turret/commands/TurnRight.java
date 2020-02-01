package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;

public class TurnRight extends BaseCommand
{
    final OperatorInterface oi;
    final TurretSubsystem turretSubsystem;

    @Inject
    public TurnRight(OperatorInterface oi , TurretSubsystem tSubsystem) {
        this.oi = oi;
        this.turretSubsystem = tSubsystem;

        this.addRequirements(this.turretSubsystem);

    }

    @Override
    public void execute() {
        turretSubsystem.turnRight();

    }

    @Override
    public void initialize() {
        log.info("Initializing");

    }
    
}