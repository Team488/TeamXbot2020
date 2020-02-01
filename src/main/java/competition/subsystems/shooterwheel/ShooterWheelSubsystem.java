package competition.subsystems.shooterwheel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.ElectricalContract;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class ShooterWheelSubsystem extends BaseSubsystem {
    
    final DoubleProperty spinWheelPowerProp;
    private ElectricalContract contract;
    public XCANTalon shooterWheelMaster;
    
    @Inject
    public ShooterWheelSubsystem(CommonLibFactory factory, PropertyFactory pf, ElectricalContract contract) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        this.contract = contract;
        spinWheelPowerProp = pf.createPersistentProperty("Spinning Wheel Power", 1);

        if(contract.isShooterWheelReady()){
            this.shooterWheelMaster = factory.createCANTalon(contract.shooterMotorMaster().channel);
        }
    }

    public void spin () {
        setPower(spinWheelPowerProp.get());
    }

    public void setPower(double power) {
        if(contract.isShooterWheelReady()){
            shooterWheelMaster.simpleSet(power);
        }
    }
  
    public void stop () {
        setPower(0);
    }
}