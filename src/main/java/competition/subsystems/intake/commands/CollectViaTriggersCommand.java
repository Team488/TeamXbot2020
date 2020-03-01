package competition.subsystems.intake.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.intake.IntakeSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class CollectViaTriggersCommand extends BaseCommand {

    private final IntakeSubsystem intake;
    private final OperatorInterface oi;

    @Inject
    public CollectViaTriggersCommand(IntakeSubsystem intake, OperatorInterface oi) {
        this.intake = intake;
        this.oi = oi;
        this.addRequirements(intake);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        double intakePower = MathUtils.deadband(oi.driverGamepad.getLeftTrigger(), oi.getJoystickDeadband());
        double ejectPower = MathUtils.deadband(oi.driverGamepad.getRightTrigger(), oi.getJoystickDeadband());

        intake.setPower(intakePower - ejectPower);
    }
}