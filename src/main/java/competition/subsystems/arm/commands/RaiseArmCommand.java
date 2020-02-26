package competition.subsystems.arm.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class RaiseArmCommand extends BaseCommand {

    final ArmSubsystem armSubsystem;
    final OperatorInterface oi;

    @Inject
    public RaiseArmCommand(OperatorInterface oi, ArmSubsystem armSubsystem) {
        this.oi = oi;
        this.armSubsystem = armSubsystem;
        this.addRequirements(this.armSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        armSubsystem.up();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }
    
}