package competition.subsystems.armlifting.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.armlifting.LiftingSubsystem;
import xbot.common.command.BaseCommand;

public class LiftingArmCommand extends BaseCommand {

    final LiftingSubsystem liftingSubsystem;
    final OperatorInterface oi;

    @Inject
    public LiftingArmCommand(OperatorInterface oi, LiftingSubsystem liftingSubsystem) {
        this.oi = oi;
        this.liftingSubsystem = liftingSubsystem;
        this.requires(this.liftingSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        liftingSubsystem.up();
        liftingSubsystem.down();
    }
    
}