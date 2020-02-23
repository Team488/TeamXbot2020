package competition.subsystems.climber.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.climber.ClimberSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class DynamicClimbCommand extends BaseCommand {

    private final ClimberSubsystem climber;
    private final OperatorInterface oi;

    @Inject
    public DynamicClimbCommand(ClimberSubsystem climber, OperatorInterface oi) {
        this.climber = climber;
        this.oi = oi;
        this.addRequirements(climber);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        // left is extend, right is retract.
        double left = MathUtils.deadband(oi.operatorGamepad.getLeftTrigger(), oi.getJoystickDeadband());
        double right = MathUtils.deadband(oi.operatorGamepad.getRightTrigger(), oi.getJoystickDeadband());
        climber.dynamicClimb(left - right);
    }
}