package competition.subsystems.climber.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.climber.ClimberSubsystem;
import xbot.common.command.BaseCommand;

public class ExtendClimberCommand extends BaseCommand{

    final OperatorInterface oi;
    final ClimberSubsystem hangerSubsystem;

    @Inject
    public ExtendClimberCommand(OperatorInterface oi, ClimberSubsystem hangerSubsystem){
        this.oi = oi;
        this.hangerSubsystem = hangerSubsystem;
        this.addRequirements(this.hangerSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        hangerSubsystem.extend();
    }
}