package competition.subsystems.shooterwheel.commands;

import com.google.inject.Inject;

import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseSetpointCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class ShooterWheelSetPassPowerCommand extends BaseSetpointCommand {

    final ShooterWheelSubsystem wheel;
    final DoubleProperty rpmProp;

    @Inject
    public ShooterWheelSetPassPowerCommand(PropertyFactory pf, ShooterWheelSubsystem wheel) {
        super(wheel);
        pf.setPrefix(this);

        rpmProp = pf.createEphemeralProperty("Pass RPM", 2000);

        this.wheel = wheel;
    }


    @Override
    public void initialize() {
        log.info("Initializing");
    }

    public void execute() {
        this.wheel.setTargetRPM(rpmProp.get());
    }
} 