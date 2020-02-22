package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
//import xbot.common.command.BaseCommand;
import xbot.common.command.BaseWaitForMaintainerCommand;
// import xbot.common.controls.sensors.XTimer;
// import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class ShooterWheelWaitForGoalCommand extends BaseWaitForMaintainerCommand {

    @Inject
    public ShooterWheelWaitForGoalCommand(ShooterWheelSubsystem subsystem, PropertyFactory pf) {
        super(subsystem, pf, 1);
    }
    
}