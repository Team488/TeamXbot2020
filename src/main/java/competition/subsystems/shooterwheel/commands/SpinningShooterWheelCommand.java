package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseCommand;

public class SpinningShooterWheelCommand extends BaseCommand{    
   
    final ShooterWheelSubsystem shooterWheelSubsystem;
    final OperatorInterface oi;

    @Inject
    public SpinningShooterWheelCommand(OperatorInterface oi, ShooterWheelSubsystem shooterWheelSubsystem){
        this.oi = oi;
        this.shooterWheelSubsystem = shooterWheelSubsystem;
        this.addRequirements(this.shooterWheelSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
       shooterWheelSubsystem.spin();
    }
}