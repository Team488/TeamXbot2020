package competition.subsystems.armlifting.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.armlifting.CollectorArmLiftingSubsystem;
import xbot.common.command.BaseCommand;

public class StopArmCommand extends BaseCommand{

    final CollectorArmLiftingSubsystem armSubsystem;
    final OperatorInterface oi;

    @Inject
    public StopArmCommand(OperatorInterface oi, CollectorArmLiftingSubsystem armSubsystem){
        this.oi = oi;
        this.armSubsystem = armSubsystem;
        this.addRequirements(this.armSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        //armSubsystem.stop();
    }
}