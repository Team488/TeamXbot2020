package competition.subsystems.armlifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
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
    public XCANTalon liftingCollectorArmMotor;
    final IdealElectricalContract contract;

    @Inject
    public CollectorArmLiftingSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating LiftingSubsystem");
        pf.setPrefix(this);
        liftingPowerProp = pf.createPersistentProperty("Lifting Power", 1);
        currentArmLiftHeightProp = pf.createEphemeralProperty("Arm Lift Height", 0);
        maximumArmLiftHeightProp = pf.createPersistentProperty("Maximum Arm Lift Height", 1);
        minimumArmLiftHeightProp = pf.createPersistentProperty("Minimum Arm Lift Height", -1);
        frozenPowerProp = pf.createPersistentProperty("Frozenpower", 0);
        this.contract = contract;

        if (contract.isCollectorArmLiftingReady()) {
            this.liftingCollectorArmMotor = factory.createCANTalon(contract.liftingCollectorArmMotor().channel);
        }
    }

    public void up() {
        setPower(liftingPowerProp.get());
    }

    public void frozen () {
        setPower(0);
    }

    public boolean isAtMaximum() { 
        return currentArmLiftHeight > maximumArmLiftHeight;
    }

    public boolean isAtMinimum() {
        return currentArmLiftHeight < minimumArmLiftHeight;
    } 

    public void setCurrentArmLiftHeight(int currentArmLiftHeight) {
        this.currentArmLiftHeight = currentArmLiftHeight;
    }
    
    public void setPower (double power) {

        if (isAtMinimum()) {
            power = MathUtils.constrainDouble(power, 0, 1);
        }

        if (isAtMaximum()) {
            power = MathUtils.constrainDouble(power, -1, 0);
        }
      
        if(contract.isCollectorArmLiftingReady()) {
            liftingCollectorArmMotor.simpleSet(power);
        }
    }

    public void stop () {
        setPower(0);
    }

}