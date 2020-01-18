package competition.subsystems.shooterwheel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class ShooterWheelSubsystem extends BaseSubsystem {
    
    final DoubleProperty spinWheelPowerProp;
    double power;
    

    @Inject
    public ShooterWheelSubsystem(CommonLibFactory factory, PropertyFactory pf) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        spinWheelPowerProp = pf.createPersistentProperty("Spinning Wheel Power", 1);
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