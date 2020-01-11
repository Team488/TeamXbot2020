package competition.subsystems.internalconveyor.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.internalconveyor.InternalConveyorSubsystem;
import xbot.common.command.BaseCommand;

public class StopCommand extends BaseCommand{

    final OperatorInterface oi;
    final InternalConveyorSubsystem internalConveyorSubsystem;

    @Inject
    public StopCommand(OperatorInterface oi, InternalConveyorSubsystem internalConveyorSubsystem){
        this.oi = oi;
        this.internalConveyorSubsystem = internalConveyorSubsystem;
        this.requires(internalConveyorSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        internalConveyorSubsystem.stop();
    }
}