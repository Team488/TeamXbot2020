package competition.subsystems.hood;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class HoodSubsystem extends BaseSubsystem{

    final DoubleProperty extendPowerProp;
    final DoubleProperty retractPowerProp;
    final DoubleProperty maxAngleProp;
    final DoubleProperty minAngleProp;
    final DoubleProperty currentAngleProp;
    double maxAngle;
    double minAngle;

    @Inject
    public HoodSubsystem(CommonLibFactory factory, PropertyFactory pf){
        pf.setPrefix(this);
        extendPowerProp = pf.createPersistentProperty("Extend Power", 0.5);
        retractPowerProp = pf.createPersistentProperty("Retract Power", -0.5);
        maxAngleProp = pf.createPersistentProperty("Max Angle", 1);
        minAngleProp = pf.createPersistentProperty("Min Angle", -1);
        currentAngleProp = pf.createEphemeralProperty("Current Angle", 0);

    }

    public double getAngle(){
        return 0; 
    }

    public void extend(){
        setPower(extendPowerProp.get());
    }

    public void retract(){
        setPower(retractPowerProp.get());
    }

    public boolean isFullyExtended(){
        return (getAngle() >= maxAngle);
    }

    public boolean isFullyRetracted(){
        return (getAngle() <= minAngle);
    }

    public void setPower(double power){
        if(isFullyExtended()){
            power = MathUtils.constrainDouble(power, -1, 0);
        }
        if(isFullyRetracted()){
            power = MathUtils.constrainDouble(power, 0, 1);
        }
    }

}