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
    final DoubleProperty minimumArmLiftHeightProp;
    final DoubleProperty frozenPowerProp;
    int maximumArmLiftHeight;
    int currentArmLiftHeight;
    int minimumArmLiftHeight;
    double power;

    @Inject
    public CollectorArmLiftingSubsystem(CommonLibFactory factory, PropertyFactory pf) {
        log.info("Creating LiftingSubsystem");
        pf.setPrefix(this);
        liftingPowerProp = pf.createPersistentProperty("Lifting Power", 1);
        currentArmLiftHeightProp = pf.createPersistentProperty("Arm Lift Height", 0);
        maximumArmLiftHeightProp = pf.createPersistentProperty("Maximum Arm Lift Height", 1);
        minimumArmLiftHeightProp = pf.createPersistentProperty("Minimum Arm Lift Height", -1);
        frozenPowerProp = pf.createPersistentProperty("Frozenpower", 0);
    }

    public void up() {
        setPower(liftingPowerProp.get());
    }

    public void frozen () {
        setPower(0);
        //is going to be different from stop. frozen just freezes the arm lift at a certain angle so that our team throw balls in but stop cause the arm to drop from no power
    }

    public boolean isAtMaximum() { //sets a certain distance that the arm can lift up to
        return currentArmLiftHeight >= maximumArmLiftHeight;
    }

    public boolean isAtMinimum() {
        return currentArmLiftHeight <= minimumArmLiftHeight;
    } 
    
    public void setPower (double power) {
        if (isAtMaximum()) {
            MathUtils.constrainDouble(power, -1, 0);
        }
        if (isAtMinimum()) {
            MathUtils.constrainDouble(power, 0, 1);
        }
    }

    public void stop () {
        setPower(0);
    }

}