package competition.subsystems.arm.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.arm.FrontCollectingSubsystem;
import xbot.common.command.BaseCommand;

public class StopFrontIntakeCommand extends BaseCommand{

    final FrontCollectingSubsystem frontIntakeSubsystem;
    final OperatorInterface oi;

    @Inject
    public StopFrontIntakeCommand(OperatorInterface oi, FrontCollectingSubsystem frontIntakeSubsystem){
        this.oi = oi;
        this.frontIntakeSubsystem = frontIntakeSubsystem;
        this.addRequirements(this.frontIntakeSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        frontIntakeSubsystem.stop();
    }
}