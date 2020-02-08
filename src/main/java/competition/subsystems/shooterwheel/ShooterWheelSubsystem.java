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
    private IdealElectricalContract contract;
    public XCANSparkMax shooterWheelMaster;
    public double speed;
    
    @Inject
    public ShooterWheelSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        this.contract = contract;
        spinWheelPowerProp = pf.createPersistentProperty("Spinning Wheel Power", 1);
        if(contract.isShooterWheelReady()){
            this.shooterWheelMaster = factory.createCANSparkMax(contract.shooterMotorMaster().channel, this.getPrefix(), "ShooterWheel");
        }
    }

    public void spin () {
        setPower(spinWheelPowerProp.get());
    }

    public void setPower(double power) {
        if(contract.isShooterWheelReady()){
            shooterWheelMaster.set(power);
        }
    }
    
    public boolean isAtSpeed () {
        return false;
    }

    public void stop () {
        setPower(0);
    }
}