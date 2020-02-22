package competition.subsystems.internalconveyor.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.internalconveyor.KickerSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class KickerViaTriggerCommand extends BaseCommand {

    final KickerSubsystem kicker;
    final OperatorInterface oi;

    @Inject
    public KickerViaTriggerCommand(KickerSubsystem kicker, OperatorInterface oi) {
        this.addRequirements(kicker);
        this.kicker = kicker;
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

        kicker.setPower(power);
    }
}