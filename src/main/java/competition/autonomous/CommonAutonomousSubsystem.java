package competition.autonomous;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class CommonAutonomousSubsystem extends BaseSubsystem {

    private final DoubleProperty delayBeforeAutonomousProp;
    private final DoubleProperty simpleDrivePowerProp;
    private final DoubleProperty simpleDriveTimeProp;

    @Inject
    public CommonAutonomousSubsystem(PropertyFactory pf) {
        pf.setPrefix(this);
        
        delayBeforeAutonomousProp = pf.createPersistentProperty("Delay Before Autonomous", 0.0);
        simpleDrivePowerProp = pf.createPersistentProperty("Simple Drive Power", 0.5);
        simpleDriveTimeProp = pf.createPersistentProperty("Simple Drive Time", 1);
    }

    public double getDelayBeforeAutonomous() {
        return delayBeforeAutonomousProp.get();
    }

    public double getSimpleDrivePower() {
        return simpleDrivePowerProp.get();
    }

    public double getSimpleDriveTime() {
        return simpleDriveTimeProp.get();
    }
}