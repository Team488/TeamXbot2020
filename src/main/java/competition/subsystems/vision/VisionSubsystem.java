package competition.subsystems.vision;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.pose.PoseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
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
    private DoubleProperty initialX;
    private DoubleProperty initialY;
    private DoubleProperty initialTheta;


    final LoggingLatch ambanFixAquiredLogLatch;
    final LoggingLatch ambanFixLostLogLatch;

    @Inject
    public VisionSubsystem(PropertyFactory pf, XScheduler scheduler, NetworkTableInstance netTableInstance) {
        log.info("Creating VisionSubsystem");
        pf.setPrefix(this);

        this.initialX = pf.createEphemeralProperty("Initial X Position", 0);
        this.initialY = pf.createEphemeralProperty("Initial Y Position", 0);
        this.initialTheta = pf.createEphemeralProperty("Initial Heading", 0);

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

    // TODO: check if it is set/sent to raspberry pi
    public boolean initialPosSet(){
        // check vision, compare pose position with vision position
    }

    public void sendXYThetaPos(double X, double Y, double Theta){
        this.initialX.set(X);
        this.initialY.set(Y);
        this.initialTheta.set(Theta);
    }

}
