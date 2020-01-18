package competition.subsystems.hanger.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.hanger.HangerSubsystem;
import xbot.common.command.BaseCommand;

public class RetractHangingCommand extends BaseCommand{    
   
    final HangerSubsystem hangerSubsystem;
    final OperatorInterface oi;

    @Inject
    public RetractHangingCommand(OperatorInterface oi, HangerSubsystem hangerSubsystem){
        this.oi = oi;
        this.hangerSubsystem = hangerSubsystem;
        this.requires(this.hangerSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
       hangerSubsystem.retractHanger();
    }
}