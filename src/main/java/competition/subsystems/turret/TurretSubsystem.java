package competition.subsystems.turret;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.PropertyFactory;

@Singleton
public class TurretSubsystem extends BaseSubsystem {
    
    public XCANTalon motor;
    @Inject
    public TurretSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating TurretSubsystem");
        pf.setPrefix(this);
        
        this.motor = factory.createCANTalon(contract.rotationMotor().channel);
        motor.setInverted(contract.rotationMotor().inverted);
    }



    public void setPower(double power) {
        motor.simpleSet(power);
    }
  
    public void stop () {
        setPower(0);
    }
}