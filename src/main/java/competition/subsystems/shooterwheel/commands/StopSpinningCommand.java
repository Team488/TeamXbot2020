package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseCommand;

public class StopSpinningCommand extends BaseCommand{    
   
    final ShooterWheelSubsystem shooterWheelSubsystem;
    final OperatorInterface oi;

    @Inject
    public StopSpinningCommand(OperatorInterface oi, ShooterWheelSubsystem shooterWheelSubsystem){
        this.oi = oi;
        this.shooterWheelSubsystem = shooterWheelSubsystem;
        this.requires(this.shooterWheelSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
       shooterWheelSubsystem.stop();
    }
}