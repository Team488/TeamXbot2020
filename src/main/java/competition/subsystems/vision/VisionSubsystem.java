package competition.subsystems.vision;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import xbot.common.command.BaseSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.logging.LoggingLatch;
import xbot.common.logic.Latch.EdgeType;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class VisionSubsystem extends BaseSubsystem {

    private final NetworkTableInstance netTableInstance;
    
    private final BooleanProperty ambanActiveProperty;
    private final BooleanProperty ambanFixAcquiredProperty;
    private final DoubleProperty ambanYawToTargetProperty;
    
    final LoggingLatch ambanFixAquiredLogLatch;
    final LoggingLatch ambanFixLostLogLatch;

    @Inject
    public VisionSubsystem(PropertyFactory pf, XScheduler scheduler, NetworkTableInstance netTableInstance) {
        log.info("Creating VisionSubsystem");
        pf.setPrefix(this);

        this.netTableInstance = netTableInstance;

        this.ambanActiveProperty = pf.createEphemeralProperty("Amban active", false);
        this.ambanFixAcquiredProperty = pf.createEphemeralProperty("Amban fix acquired", false);
        this.ambanYawToTargetProperty = pf.createEphemeralProperty("Amban yaw to target", 0);

        this.ambanFixAquiredLogLatch = new LoggingLatch(this.getName(), "Amban fix acquired", EdgeType.RisingEdge);
        this.ambanFixLostLogLatch = new LoggingLatch(this.getName(), "Amban fix lost", EdgeType.FallingEdge);

        scheduler.registerSubsystem(this);
    }

    @Override
    public void periodic() {
        ambanActiveProperty.set(isAmbanActive());
        ambanFixAcquiredProperty.set(isAmbanFixAcquired());
        ambanYawToTargetProperty.set(getAmbanYawToTarget());
    }

    public double getAmbanYawToTarget() {
        return getAmbanNetworkTable().getEntry("yawToTarget").getNumber(0).doubleValue();
    }

    public double getDistanceToTarget()
    {
        return 10; //TODO: Vision team please add a way to get the distance away to the target from the robot. 
    }

    public boolean isAmbanActive() {
        return getAmbanNetworkTable().getEntry("active").getBoolean(false);
    }

    public boolean isAmbanFixAcquired() {
        boolean fixAcquiredValue = getAmbanNetworkTable().getEntry("fixAcquired").getBoolean(false);

        this.ambanFixAquiredLogLatch.checkValue(fixAcquiredValue);
        this.ambanFixLostLogLatch.checkValue(fixAcquiredValue);

        return fixAcquiredValue;
    }

    private NetworkTable getAmbanNetworkTable() {
        return this.netTableInstance.getTable("amban");
    }
}
