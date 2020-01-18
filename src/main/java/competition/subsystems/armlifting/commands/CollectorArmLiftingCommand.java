package competition.subsystems.armlifting.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.armlifting.CollectorArmLiftingSubsystem;
import xbot.common.command.BaseCommand;

public class CollectorArmLiftingCommand extends BaseCommand {

    final CollectorArmLiftingSubsystem collectorArmLiftingSubsystem;
    final OperatorInterface oi;

    @Inject
    public CollectorArmLiftingCommand(OperatorInterface oi, CollectorArmLiftingSubsystem collectorArmLiftingSubsystem) {
        this.oi = oi;
        this.collectorArmLiftingSubsystem = collectorArmLiftingSubsystem;
        this.requires(this.collectorArmLiftingSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        collectorArmLiftingSubsystem.up();
    }
    
}