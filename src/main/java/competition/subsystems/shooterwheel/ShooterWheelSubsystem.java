package competition.subsystems.shooterwheel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class ShooterWheelSubsystem extends BaseSubsystem {
    
    final DoubleProperty spinWheelPowerProp;
    public final XCANSparkMax neoMasterMotor;
    public final XCANSparkMax neoFollowerMotor;
    
    
    @Inject
    public ShooterWheelSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        spinWheelPowerProp = pf.createPersistentProperty("Spinning Wheel Power", 1);

        this.neoMasterMotor = factory.createCANSparkMax(contract.shooterMotorMaster().channel, this.getPrefix(), "ShooterMaster");
        this.neoFollowerMotor = factory.createCANSparkMax(contract.shooterMotorFollower().channel, this.getPrefix(), "ShooterFollower");


    }

    public void spin () {
        setPower(spinWheelPowerProp.get());
    }

    public void setPower(double power) {
        
    }
  
    public void stop () {
        setPower(0);
    }
}