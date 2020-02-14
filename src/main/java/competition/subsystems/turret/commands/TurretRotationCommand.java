package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;

public class TurretRotationCommand extends BaseCommand
{
    final OperatorInterface oi;
    final TurretSubsystem turretSubsystem;

    @Inject
    public TurretRotationCommand(OperatorInterface oi , TurretSubsystem tSubsystem) {
        this.oi = oi;
        this.turretSubsystem = tSubsystem;
        this.addRequirements(this.turretSubsystem);
    }

    @Override
    public void execute() {
        double yAxis = oi.operatorGamepad.getRightStickX();
        turretSubsystem.setPower(yAxis);
    }

    @Override
    public void initialize() {
        log.info("Initializing");

    }
    
}