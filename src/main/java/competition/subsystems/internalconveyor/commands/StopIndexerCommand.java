package competition.subsystems.internalconveyor.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.internalconveyor.IndexerSubsystem;
import xbot.common.command.BaseCommand;

public class StopIndexerCommand extends BaseCommand{

    final OperatorInterface oi;
    final IndexerSubsystem indexer;

    @Inject
    public StopIndexerCommand(OperatorInterface oi, IndexerSubsystem indexer){
        this.oi = oi;
        this.indexer = indexer;
        this.addRequirements(indexer);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        indexer.stop();
    }
}