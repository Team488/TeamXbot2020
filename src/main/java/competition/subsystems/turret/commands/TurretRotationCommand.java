package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;

public class TurretRotationCommand extends BaseCommand
{
    //comment so i can commit this one character change
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
        double yAxis = oi.gamepad.getRightStickX();
        if(yAxis>0 && turretSubsystem.canTurnLeft() || yAxis<0 && turretSubsystem.canTurnRight())
        {
            turretSubsystem.setPower(yAxis);
        }
    }

    @Override
    public void initialize() {
        log.info("Initializing");

    }
    
}