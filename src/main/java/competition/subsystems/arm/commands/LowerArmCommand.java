package competition.subsystems.arm.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.climber.ClimberSubsystem;
import xbot.common.command.BaseCommand;

public class LowerArmCommand extends BaseCommand {

    final ArmSubsystem armSubsystem;
    final OperatorInterface oi;
    final ClimberSubsystem climber;

    @Inject
    public LowerArmCommand(OperatorInterface oi, ArmSubsystem armSubsystem, ClimberSubsystem climber) {
        this.oi = oi;
        this.armSubsystem = armSubsystem;
        this.climber = climber;
        this.addRequirements(this.armSubsystem);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        if (climber.unsafeToLowerArm()) {
            log.warn("Tried to lower arm, but hanger was extended! Will not lower arm in these conditions.");
        } else {
            armSubsystem.down();
        }
    }

    @Override
    public void execute() {

    }

    @Override
    public boolean isFinished() {
        return true;
    }

}