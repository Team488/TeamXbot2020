package competition.subsystems.intake.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.intake.IntakeSubsystem;
import xbot.common.command.BaseCommand;

public class StopIntakeCommand extends BaseCommand{

    final IntakeSubsystem intakeSubsystem;
    final OperatorInterface oi;

    @Inject
    public StopIntakeCommand(OperatorInterface oi, IntakeSubsystem intakeSubsystem){
        this.oi = oi;
        this.intakeSubsystem = intakeSubsystem;
        this.addRequirements(this.intakeSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        intakeSubsystem.stop();
    }
}