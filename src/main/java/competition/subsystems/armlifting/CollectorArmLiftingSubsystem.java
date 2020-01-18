package competition.subsystems.armlifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
 
@Singleton
public class CollectorArmLiftingSubsystem extends BaseSubsystem {
    
    final DoubleProperty liftingPowerProp;
    final DoubleProperty currentArmLiftHeightProp;
    final DoubleProperty maximumArmLiftHeightProp;
    int maximumArmLiftHeight;
    int currentArmLiftHeight;
    double power;

    @Inject
    public CollectorArmLiftingSubsystem(CommonLibFactory factory, PropertyFactory pf) {
        log.info("Creating LiftingSubsystem");
        pf.setPrefix(this);
        liftingPowerProp = pf.createPersistentProperty("Lifting Power", 1);
        currentArmLiftHeightProp = pf.createPersistentProperty("Arm Lift Height", 1);
        maximumArmLiftHeightProp = pf.createPersistentProperty("Maximum Arm Lift Height", 10);
    }

    public void up() {
        setPower(liftingPowerProp.get());
    }

    public void maximum(double power) { //sets a certain distance that the arm can lift up to
        MathUtils.constrainDouble(power, -1, maximumArmLiftHeight);
    }
    
    public void setPower (double power) {
        if (currentArmLiftHeight >= maximumArmLiftHeight) {
            maximum(power);
        }
    }

    public void stop () {
        setPower(0);
    }

}