package competition.subsystems.hanger.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.hanger.HangerSubsystem;
import xbot.common.command.BaseCommand;

public class ExtendHangerCommand extends BaseCommand{

    final OperatorInterface oi;
    final HangerSubsystem hangerSubsystem;

    @Inject
    public ExtendHangerCommand(OperatorInterface oi, HangerSubsystem hangerSubsystem){
        this.oi = oi;
        this.hangerSubsystem = hangerSubsystem;
        this.addRequirements(this.hangerSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        hangerSubsystem.extendHanger();
    }
}