package competition.subsystems.armlifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class FrontCollectingSubsystem extends BaseSubsystem { // maybe call this FrontCollectionSubsystem
    
    final DoubleProperty intakePowerProp;
    double power;
    int currentTotalBalls = 0;

    @Inject
    public FrontCollectingSubsystem(CommonLibFactory factory, PropertyFactory pf) {
        log.info("Creating CollectingSubsystem");
        pf.setPrefix(this);
        intakePowerProp = pf.createPersistentProperty("Intake Power", 1);

        // do ^ for as many as you can to adjust during dashboard
        // make a set power for both subsystems
        // make a stop for both subsystems as the default

    }

    public void intake() {
        setPower(intakePowerProp.get());
    }

    public void setPower(double power){
        
    }
    
    public boolean isAtCapacity() {
        if (currentTotalBalls >= 5) {
            return false;
        } else {
            return true;
        }
    }

    public void stop () {
        setPower(0);

    }
}