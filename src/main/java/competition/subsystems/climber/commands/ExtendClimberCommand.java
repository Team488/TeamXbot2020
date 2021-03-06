package competition.subsystems.climber.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.climber.ClimberSubsystem;
import xbot.common.command.BaseCommand;

public class ExtendClimberCommand extends BaseCommand{

    final OperatorInterface oi;
    final ClimberSubsystem climberSubsystem;

    @Inject
    public ExtendClimberCommand(OperatorInterface oi, ClimberSubsystem climberSubsystem){
        this.oi = oi;
        this.climberSubsystem = climberSubsystem;
        this.addRequirements(this.climberSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        climberSubsystem.extend();
    }
}