package competition.subsystems.hood;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
//import xbot.common.injection.ElectricalContract;
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
    private IdealElectricalContract contract;
    double maxAngle;
    double minAngle;
    public XCANTalon hoodMotor;

    @Inject
    public HoodSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract){
        pf.setPrefix(this);
        this.contract = contract;
        extendPowerProp = pf.createPersistentProperty("Extend Power", 1);
        retractPowerProp = pf.createPersistentProperty("Retract Power", -1);
        maxAngleProp = pf.createPersistentProperty("Max Angle", 5);
        minAngleProp = pf.createPersistentProperty("Min Angle", -5);
        currentAngleProp = pf.createEphemeralProperty("Current Angle", 0);

        if (contract.isHoodReady()) {
             this.hoodMotor = factory.createCANTalon(contract.hoodMotor().channel);
        }

        maxAngle = 5;
        minAngle = -5;

    }

    public double getAngle(){
        return 0; //return angle
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

        if (contract.isHoodReady()) {
            hoodMotor.simpleSet(power);
        }
    }

}