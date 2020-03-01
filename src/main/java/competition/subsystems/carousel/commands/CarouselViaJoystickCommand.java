package competition.subsystems.carousel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.carousel.CarouselSubsystem;
import xbot.common.math.MathUtils;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class CarouselViaJoystickCommand extends CarouselManagerCommand {

    private final OperatorInterface oi;
    private final DoubleProperty maxPower;

    @Inject
    public CarouselViaJoystickCommand(CarouselSubsystem carousel, OperatorInterface oi, PropertyFactory pf) {
        super(carousel, pf);
        this.oi = oi;
        pf.setPrefix(this);

        maxPower = pf.createPersistentProperty("MaxPower", 1.0);

        this.addRequirements(carousel);

        setPowerSupplier(this::getJoystickPowers);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    private double getJoystickPowers() {
        double operatorPower = MathUtils.deadband(oi.operatorGamepad.getLeftVector().x, oi.getJoystickDeadband());
        double manualOperatorPower = MathUtils.deadband(oi.manualOperatorGamepad.getLeftVector().x, oi.getJoystickDeadband());
        
        double power = (operatorPower + manualOperatorPower) * maxPower.get();
        return power;
    }
}