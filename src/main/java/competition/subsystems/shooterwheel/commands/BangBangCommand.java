package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class BangBangCommand extends BaseCommand{

    final ShooterWheelSubsystem shooterWheelSubsystem;
    final OperatorInterface oi;
    final DoubleProperty maxPowerProp;

    @Inject
    public BangBangCommand(OperatorInterface oi, ShooterWheelSubsystem shooterWheelSubsystem, PropertyFactory pf) {
        this.oi = oi;
        pf.setPrefix(this);

        maxPowerProp = pf.createEphemeralProperty("Max Power", 0.5);

        this.shooterWheelSubsystem = shooterWheelSubsystem;
        this.addRequirements(this.shooterWheelSubsystem);
    }


    @Override
    public void initialize() {
        log.info("Initializing");
    }

    public void execute(){
        if(shooterWheelSubsystem.getCurrentRPM() < shooterWheelSubsystem.getTargetRPM()){
            shooterWheelSubsystem.setPower(1);
        } else if (shooterWheelSubsystem.getCurrentRPM() > shooterWheelSubsystem.getTargetRPM()){
            shooterWheelSubsystem.setPower(0);
        }
    }
} 