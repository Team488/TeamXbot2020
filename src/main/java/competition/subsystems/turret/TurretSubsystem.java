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
    DoubleProperty maxAngleProp;
    DoubleProperty minAngleProp;
    DoubleProperty turnSpeedProp;

    @Inject
    public TurretSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating TurretSubsystem");
        pf.setPrefix(this);
        
        maxAngleProp = pf.createPersistentProperty("MaxAngle",180);
        minAngleProp = pf.createPersistentProperty("MinAngle", -180);
        turnSpeedProp = pf.createPersistentProperty("TurnSpeed", .03); 


        this.motor = factory.createCANTalon(contract.rotationMotor().channel);
        motor.setInverted(contract.rotationMotor().inverted);
    }

    public void turnLeft()
    {
        motor.simpleSet(-turnSpeedProp.get());
    }

    public void turnRight()
    {
        motor.simpleSet(turnSpeedProp.get());
    }

    public void setPower(double power) {
        motor.simpleSet(power);
    }
    
    public boolean canTurnRight(double currentAngle)
    {
        return currentAngle <= maxAngleProp.get()-turnSpeedProp.get();
    }

    public boolean canTurnLeft(double currentAngle)
    {
        return currentAngle >= minAngleProp.get()+turnSpeedProp.get();
    }

    public void stop() {
        setPower(0);
    }
}