package competition.subsystems.internalconveyor.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.internalconveyor.KickerSubsystem;
import xbot.common.command.BaseCommand;

public class ReverseKickerCommand extends BaseCommand{

    final OperatorInterface oi;
    final KickerSubsystem kicker;

    @Inject
    public ReverseKickerCommand (OperatorInterface oi, KickerSubsystem kicker){
        this.oi = oi;
        this.kicker = kicker;
        this.addRequirements(kicker);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        kicker.reverse();
    }
}