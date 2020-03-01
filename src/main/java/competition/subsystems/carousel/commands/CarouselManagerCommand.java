package competition.subsystems.carousel.commands;

import java.util.function.Supplier;

import com.google.inject.Inject;

import competition.subsystems.carousel.CarouselSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.logic.TimeStableValidator;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class CarouselManagerCommand extends BaseCommand {

    public enum CarouselDirection {
        Forward, Reverse
    }

    CarouselDirection direction;
    Supplier<Double> powerSupplier;
    private final CarouselSubsystem carousel;
    TimeStableValidator forwardValidator;
    TimeStableValidator reverseValidator;

    private final DoubleProperty forwardDurationProp;
    private final DoubleProperty reverseDurationProp;

    @Inject
    public CarouselManagerCommand(CarouselSubsystem carousel, PropertyFactory pf) {
        this.carousel = carousel;
        pf.setPrefix(this);
        this.addRequirements(carousel);

        forwardDurationProp = pf.createPersistentProperty("Forward Duration", 0.9);
        reverseDurationProp = pf.createPersistentProperty("Reverse Duration", 0.1);
        forwardValidator = new TimeStableValidator(() -> forwardDurationProp.get());
        reverseValidator = new TimeStableValidator(() -> reverseDurationProp.get());
    }

    public void setPowerSupplier(Supplier<Double> powerSupplier) {
        this.powerSupplier = powerSupplier;
    }

    private double getPower() {
        if (powerSupplier == null) {
            return 0;
        }
        return powerSupplier.get();
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        direction = CarouselDirection.Forward;
    }

    @Override
    public void execute() {
        double power = 0;
        updateDirection();

        switch (direction) {
        case Forward:
            power = getPower();
            break;
        case Reverse:
            power = -getPower();
        }

        carousel.setPower(power);
    }

    private void updateDirection() {
        switch (direction) {
        case Forward:
            boolean forwardStable = forwardValidator.checkStable(true);
            reverseValidator.checkStable(false);
            if (forwardStable) {
                direction = CarouselDirection.Reverse;
                reverseValidator.checkStable(true);
            }
            break;
        case Reverse:
            boolean reverseStable = reverseValidator.checkStable(true);
            forwardValidator.checkStable(false);
            if (reverseStable) {
                direction = CarouselDirection.Forward;
                forwardValidator.checkStable(true);
            }
            break;
        }
    }

    public double getForwardTime() {
        return forwardDurationProp.get();
    }

    public double getReverseTime() {
        return reverseDurationProp.get();
    }
}