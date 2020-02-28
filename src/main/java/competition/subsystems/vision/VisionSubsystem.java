package competition.subsystems.vision;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.pose.PoseSubsystem;
import competition.subsystems.vision.commands.sendInitialPos;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import xbot.common.command.BaseSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.logging.LoggingLatch;
import xbot.common.logic.Latch.EdgeType;
import xbot.common.math.FieldPose;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class VisionSubsystem extends BaseSubsystem {

    private final PoseSubsystem pose;

    private final NetworkTableInstance netTableInstance;

    private final BooleanProperty ambanActiveProperty;
    private final BooleanProperty ambanFixAcquiredProperty;
    private final DoubleProperty ambanYawToTargetProperty;
    private DoubleProperty initialX;
    private DoubleProperty initialY;
    private DoubleProperty initialTheta;

    private sendInitialPos b;



    final LoggingLatch ambanFixAquiredLogLatch;
    final LoggingLatch ambanFixLostLogLatch;

    @Inject
    public VisionSubsystem(PropertyFactory pf, XScheduler scheduler,
            NetworkTableInstance netTableInstance, PoseSubsystem pose) {
        log.info("Creating VisionSubsystem");
        pf.setPrefix(this);

        this.initialX = pf.createEphemeralProperty("Initial X Position", 0);
        this.initialY = pf.createEphemeralProperty("Initial Y Position", 0);
        this.initialTheta = pf.createEphemeralProperty("Initial Heading", 0);

        this.pose = pose;
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

        publishPose();
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

    private void publishPose() {
        NetworkTable poseTable = getPoseNetworkTable();
        FieldPose fieldPose = this.pose.getCurrentFieldPose();

        poseTable.getEntry("navReady").setBoolean(this.pose.getNavXReady());
        poseTable.getEntry("x").setNumber(fieldPose.getPoint().x);
        poseTable.getEntry("y").setNumber(fieldPose.getPoint().y);
        poseTable.getEntry("xVelocity").setNumber(this.pose.getCurrentVelocity().x);
        poseTable.getEntry("yVelocity").setNumber(this.pose.getCurrentVelocity().y);
        poseTable.getEntry("heading").setNumber(pose.getCurrentHeading().getValue());
        poseTable.getEntry("headingAngularVelocity").setNumber(pose.getCurrentHeadingAngularVelocity());
        poseTable.getEntry("pitch").setNumber(this.pose.getRobotPitch());
        poseTable.getEntry("roll").setNumber(this.pose.getRobotRoll());
        poseTable.getEntry("yawAngularVelocity").setNumber(this.pose.getYawAngularVelocity());
    }

    private NetworkTable getAmbanNetworkTable() {
        return this.netTableInstance.getTable("amban");
    }

    // check vision, compare pose position with vision position, check if it is set/sent to raspberry pi
    public boolean checkInitialPosSet(){
        if(initialX.get() == b.X && initialTheta.get() == b.Theta && initialY.get() == b.Y){
            return true;
        }else{
            return false;
        }
    }

    public void sendXYThetaPos(double X, double Y, double Theta){
        this.initialX.set(X);
        this.initialY.set(Y);
        this.initialTheta.set(Theta);
    }

    private NetworkTable getPoseNetworkTable() {
        return this.netTableInstance.getTable("pose");
    }
}
