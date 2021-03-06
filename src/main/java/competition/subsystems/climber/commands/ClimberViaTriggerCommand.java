package competition.subsystems.climber.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.climber.ClimberSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class ClimberViaTriggerCommand extends BaseCommand{

    final OperatorInterface oi;
    final ClimberSubsystem climberSubsystem;

    @Inject
    public ClimberViaTriggerCommand(OperatorInterface oi, ClimberSubsystem climberSubsystem){
        this.oi = oi;
        this.climberSubsystem = climberSubsystem;
        this.addRequirements(this.climberSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        double extend = MathUtils.deadband(oi.operatorGamepad.getLeftTrigger(), oi.getJoystickDeadband());
        extend +=  MathUtils.deadband(oi.manualOperatorGamepad.getLeftTrigger(), oi.getJoystickDeadband());
        double retract = MathUtils.deadband(oi.operatorGamepad.getRightTrigger(), oi.getJoystickDeadband()) * -1;
        retract += MathUtils.deadband(oi.manualOperatorGamepad.getRightTrigger(), oi.getJoystickDeadband()) * -1;
        double power = extend + retract;
        climberSubsystem.setPower(power);
    }
}