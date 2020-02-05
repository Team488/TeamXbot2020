package competition.subsystems.turret;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class TurretSubsystem extends BaseSubsystem {
    
    public XCANTalon motor;
    public double currentAngle;
    DoubleProperty maxAngleProp;
    DoubleProperty minAngleProp;
    DoubleProperty turnPowerProp;

    @Inject
    public TurretSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating TurretSubsystem");
        pf.setPrefix(this);
        
        maxAngleProp = pf.createPersistentProperty("MaxAngle",180);
        minAngleProp = pf.createPersistentProperty("MinAngle", -180);
        turnPowerProp = pf.createPersistentProperty("TurnSpeed", .03); 

        if(contract.isTurretReady())
        {
            this.motor = factory.createCANTalon(contract.rotationMotor().channel);
            motor.setInverted(contract.rotationMotor().inverted);
        }
    }

    public void turnLeft()
    {
        motor.simpleSet(turnPowerProp.get());
    }

    public void turnRight()
    {
        motor.simpleSet(-turnPowerProp.get());
    }

    public void setPower(double power) {
        if(power<0 && canTurnLeft())
        {
            motor.simpleSet(power);
        }else if(power>0 && canTurnRight())
        {
            motor.simpleSet(power);
        }
    }
    
    public boolean canTurnRight()
    {
        return getCurrentAngle() <= maxAngleProp.get();
    }

    public boolean canTurnLeft()
    {
        return getCurrentAngle() >= minAngleProp.get();
    }

    public double getCurrentAngle()
    {
        return currentAngle;
    }

    public void stop() {
        setPower(0);
    }
}






