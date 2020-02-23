package competition.subsystems.carousel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.carousel.CarouselSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class CarouselViaJoystickCommand extends BaseCommand {

    private final CarouselSubsystem carousel;
    private final OperatorInterface oi;

    @Inject
    public CarouselViaJoystickCommand(CarouselSubsystem carousel, OperatorInterface oi) {
        this.carousel = carousel;
        this.oi = oi;
        this.addRequirements(carousel);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        double power = MathUtils.deadband(oi.operatorGamepad.getLeftVector().x, oi.getJoystickDeadband());
        carousel.setPower(power);
    }
}