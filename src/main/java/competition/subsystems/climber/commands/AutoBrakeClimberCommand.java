package competition.subsystems.climber.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.climber.ClimberSubsystem;
import xbot.common.command.BaseCommand;

public class AutoBrakeClimberCommand extends BaseCommand{

    // public final XSolenoid climbSolenoid;
    final ClimberSubsystem climbSubsystem;
    final OperatorInterface oi;

    @Inject
    public AutoBrakeClimberCommand(OperatorInterface oi, ClimberSubsystem climbSubsystem){
        this.oi = oi;
        this.climbSubsystem = climbSubsystem;
        this.addRequirements(this.climbSubsystem);

    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        climbSubsystem.autoBrake();
    }

}