package competition.subsystems.hood.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.hood.HoodSubsystem;
import xbot.common.command.BaseCommand;

public class StopHoodCommand extends BaseCommand{

    final HoodSubsystem hoodSubsystem;
    final OperatorInterface oi;

    @Inject
    public StopHoodCommand(OperatorInterface oi, HoodSubsystem hoodSubsystem){
        this.oi = oi;
        this.hoodSubsystem = hoodSubsystem;
        this.addRequirements(this.hoodSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        hoodSubsystem.stop();
    }
}