package competition.subsystems.drive;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.log4j.Logger;

import competition.IdealElectricalContract;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.util.Units;
import scala.annotation.meta.param;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.math.XYPair;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.properties.XPropertyManager;
import xbot.common.subsystems.drive.BaseDriveSubsystem;

@Singleton
public class DriveSubsystem extends BaseDriveSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);


    // would need to run characterization tool for these values (ks, kv, track width):
    public static final double ks = 1.06; // static gain
    public static final double kv = 0.0635; // velocity gain
    public static final double trackWidth = 1.09; // track width

    private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(trackWidth);
    private DifferentialDriveWheelSpeeds wheelSpeeds;
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(ks, kv);

    public XCANSparkMax leftMaster;
    public XCANSparkMax leftFollower;
    public XCANSparkMax rightMaster;
    public XCANSparkMax rightFollower;

    private final DoubleProperty rawLeftRotationsProp;
    private final DoubleProperty rawRightRotationsProp;

    final IdealElectricalContract contract;

    private final PIDManager positionalPid;
    private final PIDManager rotatePid;
    private final PIDManager rotateDecayPid;

    @Inject
    public DriveSubsystem(CommonLibFactory factory, XPropertyManager propManager, IdealElectricalContract contract,
            PropertyFactory pf, PIDFactory pidf) {
        log.info("Creating DriveSubsystem");
        this.contract = contract;
        pf.setPrefix(this);

        if (contract.isDriveReady()) {
            this.leftMaster = factory.createCANSparkMax(contract.leftRearDriveNeo().channel, this.getPrefix(),
                    "LeftMaster");
            this.leftFollower = factory.createCANSparkMax(contract.leftFrontDriveNeo().channel, this.getPrefix(),
                    "LeftFollower");
            this.rightMaster = factory.createCANSparkMax(contract.rightRearDriveNeo().channel, this.getPrefix(),
                    "RightMaster");
            this.rightFollower = factory.createCANSparkMax(contract.rightFrontDriveNeo().channel, this.getPrefix(),
                    "RightFollower");

            leftMaster.setInverted(contract.leftRearDriveNeo().inverted);
            leftFollower.follow(leftMaster, contract.leftFrontDriveNeo().inverted);
            rightMaster.setInverted(contract.rightRearDriveNeo().inverted);
            rightFollower.follow(rightMaster, contract.rightFrontDriveNeo().inverted);
        }

        rawLeftRotationsProp = pf.createEphemeralProperty("RawLeftRotations", 0.0);
        rawRightRotationsProp = pf.createEphemeralProperty("RawRightRotations", 0.0);

        positionalPid = pidf.createPIDManager(this.getPrefix() + "PositionPID", 0.03125, 0, 0);
        rotatePid = pidf.createPIDManager(this.getPrefix() + "RotatePID", 0.02222, 0, 0);
        rotateDecayPid = pidf.createPIDManager(this.getPrefix() + "RotateDecayPID", 0, 0, 0.01);
    }

    @Override
    public PIDManager getPositionalPid() {
        return positionalPid;
    }

    @Override
    public PIDManager getRotateToHeadingPid() {
        return rotatePid;
    }

    @Override
    public void move(XYPair translate, double rotate) {
        double left = translate.y - rotate;
        double right = translate.y + rotate;

        setRawPower(left, right);
    }

    public void kinematicsDrive(double linearVelocity, double angularVelocity) {
        var chassisSpeed = new ChassisSpeeds(linearVelocity, 0.0, angularVelocity); // 0.0 = no y directional
        wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeed);
        
        double leftSetPoint = wheelSpeeds.leftMetersPerSecond;
        double rightSetPoint = wheelSpeeds.rightMetersPerSecond;
        
        double leftPower = feedforward.calculate(leftSetPoint);
        double rightPower = feedforward.calculate(rightSetPoint);

        leftMaster.setVoltage(leftPower);
        rightMaster.setVoltage(rightPower);
    }

    public void setRawPower(double left, double right) {
        this.leftMaster.set(left);
        this.rightMaster.set(right);
    }

    public double getLeftTotalDistance() {
        if (contract.isDriveReady()) {
            return leftMaster.getPosition();
        }
        return 0;
    }

    public double getRightTotalDistance() {
        if (contract.isDriveReady()) {
            return rightMaster.getPosition();
        }
        return 0;
    }

    @Override
    public void periodic() {
        rawLeftRotationsProp.set(getLeftTotalDistance());
        rawRightRotationsProp.set(getRightTotalDistance());
    }

    @Override
    public double getTransverseDistance() {
        // Always zero, as this robot has no transverse distance sensor (and hopefully no transverse movement)
        return 0;
    }

    @Override
    public PIDManager getRotateDecayPid() {
        return rotateDecayPid;
    }
}
