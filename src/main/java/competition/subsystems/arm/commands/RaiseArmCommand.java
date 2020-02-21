package competition.subsystems.arm.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.arm.ArmSubsystem;
import xbot.common.command.BaseCommand;

public class RaiseArmCommand extends BaseCommand {

    final ArmSubsystem ArmSubsystem;
    final OperatorInterface oi;

    @Inject
    public RaiseArmCommand(OperatorInterface oi, ArmSubsystem ArmSubsystem) {
        this.oi = oi;
        this.ArmSubsystem = ArmSubsystem;
        this.addRequirements(this.ArmSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        ArmSubsystem.up();
    }
    
}