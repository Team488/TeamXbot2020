package competition.subsystems.armlifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
 
@Singleton
public class LiftingSubsystem extends BaseSubsystem {
    
    final DoubleProperty liftingPowerProp;
    final DoubleProperty loweringPowerProp;
    int maximumLiftHeight;
    int currentLiftHeight;


    @Inject
    public LiftingSubsystem(CommonLibFactory factory, PropertyFactory pf) {
        log.info("Creating LiftingSubsystem");
        pf.setPrefix(this);
        liftingPowerProp = pf.createPersistentProperty("Lifting Power", 1);
        loweringPowerProp = pf.createPersistentProperty("Lowering Power", 1);

    }

    public void up() {
        setPower(liftingPowerProp.get());
    }
    
    public void down() {
        setPower(loweringPowerProp.get());
    }

    public void maximum() { //sets a certain distance that the arm can lift up to
        if (currentLiftHeight == maximumLiftHeight) {
            //the robot won't be able to lift higher
        }

    }

    public void setPower (double power) {

    }

    public void stop () {
        setPower(0);

    }

}