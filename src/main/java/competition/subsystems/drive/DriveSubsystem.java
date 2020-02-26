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
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class DriveSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);


    // would need to run characterization tool for these values (ks, kv, track width):
    public static final double ks = 0; // static gain
    public static final double kv = 0; // velocity gain
    public static final double trackWidth = 0; // track width
    public static final double leftMaxVoltage = 0; // maximum voltage for left motor
    public static final double rightMaxVoltage = 0;  // maximum voltage for right motor



    private DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(trackWidth);
    private DifferentialDriveWheelSpeeds wheelSpeeds;
    private SimpleMotorFeedforward feedforward = new SimpleMotorFeedforward(ks, kv);
    

    public XCANSparkMax leftMaster;
    public XCANSparkMax leftFollower;
    public XCANSparkMax rightMaster;
    public XCANSparkMax rightFollower;

    final DoubleProperty rawLeftRotationsProp;
    final DoubleProperty rawRightRotationsProp;

    final IdealElectricalContract contract;

    @Inject
    public DriveSubsystem(CommonLibFactory factory, XPropertyManager propManager, IdealElectricalContract contract,
            PropertyFactory pf) {
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
    }

    public void tankDrive(double leftPower, double rightPower) {
        setRawPower(leftPower, rightPower);
    }

    public void arcadeDrive(double translate, double rotate) {
        double left = translate - rotate;
        double right = translate + rotate;

        setRawPower(left, right);
    }
    
    public void kinematicsDrive(double linearVelocity, double angularVelocity) {
        var chassisSpeed = new ChassisSpeeds(linearVelocity, 0.0, angularVelocity); // 0.0 = no y directional
        wheelSpeeds = kinematics.toWheelSpeeds(chassisSpeed);
        
        double leftPower = getLeftSpeedVoltage(wheelSpeeds)/leftMaxVoltage;
        double rightPower = getRightSpeedVoltage(wheelSpeeds)/rightMaxVoltage;

        leftMaster.setVoltage(leftPower);
        rightMaster.setVoltage(rightPower);
    }

    public double getLeftSpeedVoltage(DifferentialDriveWheelSpeeds speeds) {
        double leftSetPoint = speeds.leftMetersPerSecond;
        final double leftFeedForward = feedforward.calculate(leftSetPoint);
        return leftFeedForward;
}

    public double getRightSpeedVoltage(DifferentialDriveWheelSpeeds speeds) {
        double rightSetPoint = speeds.rightMetersPerSecond;
        final double rightFeedForward = feedforward.calculate(rightSetPoint);
        return rightFeedForward;
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
}
