package competition.subsystems.internalconveyor.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.internalconveyor.IndexerSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class IndexerViaTriggerCommand extends BaseCommand {

    final IndexerSubsystem indexer;
    final OperatorInterface oi;

    @Inject
    public IndexerViaTriggerCommand(IndexerSubsystem indexer, OperatorInterface oi) {
        this.addRequirements(indexer);
        this.indexer = indexer;
        this.oi = oi;
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        double power = MathUtils.deadband(oi.operatorGamepad.getLeftTrigger(), oi.getJoystickDeadband());
        power = MathUtils.constrainDouble(power, 0, 1);

        indexer.setPower(power);
    }
}