package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseCommand;

public class BangBangCommand extends BaseCommand{

    final ShooterWheelSubsystem shooterWheelSubsystem;
    final OperatorInterface oi;

    @Inject
    public BangBangCommand(OperatorInterface oi, ShooterWheelSubsystem shooterWheelSubsystem) {
        this.oi = oi;
        this.shooterWheelSubsystem = shooterWheelSubsystem;
        this.addRequirements(this.shooterWheelSubsystem);
    }


    @Override
    public void initialize() {
        log.info("Initializing");
    }

    public void execute(){
        if(shooterWheelSubsystem.getCurrentSpeed() < shooterWheelSubsystem.getTargetSpeed()){
            shooterWheelSubsystem.setPower(1);
        } else if (shooterWheelSubsystem.getCurrentSpeed() > shooterWheelSubsystem.getTargetSpeed()){
            shooterWheelSubsystem.setPower(0);
        }
    }
} 