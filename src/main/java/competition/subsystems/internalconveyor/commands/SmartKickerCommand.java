package competition.subsystems.internalconveyor.commands;

import com.google.inject.Inject;

import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.internalconveyor.KickerSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.command.BaseSetpointSubsystem;

public class SmartKickerCommand extends BaseCommand {

    KickerSubsystem kicker;
    ShooterWheelSubsystem wheel;
    HoodSubsystem hood;
    TurretSubsystem turret;

    boolean neverGiveUp = false;

    @Inject
    public SmartKickerCommand(KickerSubsystem kicker, ShooterWheelSubsystem wheel, HoodSubsystem hood, TurretSubsystem turret) {
        this.kicker = kicker;
        this.wheel = wheel;
        this.hood = hood;
        this.turret = turret;

        this.addRequirements(kicker);
    }

    @Override
    public void initialize() {
        log.info("Initialized");
        neverGiveUp = false;
    }

    @Override
    public void execute() {
        if (neverGiveUp) {
            kicker.setPower(1);
        } else {
            kicker.setPower(0);
            if (atGoal(wheel) && atGoal(hood)/* && atGoal(turret)*/) { //TODO: Once turret is moving again, enable this
                neverGiveUp = true;
            }
        }
    }

    private boolean atGoal(BaseSetpointSubsystem subsystem) {
        return subsystem.isMaintainerAtGoal();
    }
}