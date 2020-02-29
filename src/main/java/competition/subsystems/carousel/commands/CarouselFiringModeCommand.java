package competition.subsystems.carousel.commands;

import com.google.inject.Inject;

import competition.subsystems.carousel.CarouselSubsystem;
import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.internalconveyor.KickerSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.command.BaseSetpointSubsystem;

public class CarouselFiringModeCommand extends BaseCommand {

    private final CarouselSubsystem carousel;
    private final ShooterWheelSubsystem wheel;
    private final KickerSubsystem kicker;
    private final TurretSubsystem turret;
    private final HoodSubsystem hood;

    private boolean streamFire;

    @Inject
    public CarouselFiringModeCommand(
        CarouselSubsystem carousel,
        ShooterWheelSubsystem wheel,
        KickerSubsystem kicker,
        TurretSubsystem turret,
        HoodSubsystem hood
    ) {
        this.carousel = carousel;
        this.wheel = wheel;
        this.kicker = kicker;
        this.turret = turret;
        this.hood = hood;
    }

    public void setStreamFire(boolean streamFireOn) {
        streamFire = streamFireOn;
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    private boolean readyToSpin() {
        boolean ready = atGoal(turret) && atGoal(hood) && kicker.isKickerLikelyClear() && shooterMovingSome();

        // if we're not in stream fire, then we are in accurate mode.
        if (!streamFire) {
            ready &= atGoal(wheel);
        }

        return ready;
    }

    private boolean atGoal(BaseSetpointSubsystem subsystem) {
        return subsystem.isMaintainerAtGoal();
    }

    private boolean shooterMovingSome() {
        return wheel.getCurrentRPM() > 500;
    }
}