package competition.subsystems.internalconveyor.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.internalconveyor.InternalConveyorSubsystem;
import xbot.common.command.BaseCommand;

public class OuttakeCommand extends BaseCommand{

    final OperatorInterface oi;
    final InternalConveyorSubsystem internalConveyorSubsystem;

    @Inject
    public OuttakeCommand (OperatorInterface oi, InternalConveyorSubsystem internalConveyorSubsystem){
        this.oi = oi;
        this.internalConveyorSubsystem = internalConveyorSubsystem;
        this.addRequirements(internalConveyorSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        internalConveyorSubsystem.outtake();
    }
}