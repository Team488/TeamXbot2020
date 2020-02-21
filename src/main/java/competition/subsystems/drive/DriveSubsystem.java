package competition.subsystems.drive;

import com.google.inject.Inject;
import com.google.inject.Singleton;


import org.apache.log4j.Logger;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class DriveSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);

    public XCANSparkMax leftMaster;
    public XCANSparkMax leftFollower;
    public XCANSparkMax rightMaster;
    public XCANSparkMax rightFollower;

    final DoubleProperty rawLeftRotationsProp;
    final DoubleProperty rawRightRotationsProp;

    private final IdealElectricalContract contract;


    @Inject
    public DriveSubsystem(
        CommonLibFactory factory, 
        XPropertyManager propManager, 
        IdealElectricalContract contract,
        PropertyFactory pf) {
        log.info("Creating DriveSubsystem");

        pf.setPrefix(this);
        this.contract = contract;
       
        if (contract.isDriveReady()) {
            this.leftMaster = factory.createCANSparkMax(contract.leftRearDriveNeo().channel, this.getPrefix(), "LeftMaster");
            this.leftFollower = factory.createCANSparkMax(contract.leftFrontDriveNeo().channel, this.getPrefix(), "LeftFollower");
            this.rightMaster = factory.createCANSparkMax(contract.rightRearDriveNeo().channel, this.getPrefix(), "RightMaster");
            this.rightFollower = factory.createCANSparkMax(contract.rightFrontDriveNeo().channel, this.getPrefix(), "RightFollower");

            leftMaster.setInverted(contract.leftRearDriveNeo().inverted);
            leftFollower.follow(leftMaster, contract.leftFrontDriveNeo().inverted);
            rightMaster.setInverted(contract.rightRearDriveNeo().inverted);
            rightFollower.follow(rightMaster, contract.rightFrontDriveNeo().inverted);
        }

        rawLeftRotationsProp = pf.createEphemeralProperty("RawLeftRotations", 0.0);
        rawRightRotationsProp = pf.createEphemeralProperty("RawRightRotations", 0.0);
    }

    public void tankDrive(double leftPower, double rightPower) {
        rawSet(leftPower, rightPower);
    }

    public void arcadeDrive(double translate, double rotate) {
        double left = translate - rotate;
        double right = translate + rotate;

        rawSet(left, right);
    }

    private void rawSet(double left, double right) {
        if (contract.isDriveReady()) {
            this.leftMaster.set(left);
            this.rightMaster.set(right); 
        }
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
