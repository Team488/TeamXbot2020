package competition.subsystems.hood.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.hood.HoodSubsystem;
import xbot.common.command.BaseCommand;

public class RetractHoodCommand extends BaseCommand{

    final OperatorInterface oi;
    final HoodSubsystem hoodSubsystem;

    @Inject
    public RetractHoodCommand(OperatorInterface oi, HoodSubsystem hoodSubsystem){
        this.oi = oi;
        this.hoodSubsystem = hoodSubsystem;
        this.addRequirements(hoodSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        hoodSubsystem.retract();
    }
}