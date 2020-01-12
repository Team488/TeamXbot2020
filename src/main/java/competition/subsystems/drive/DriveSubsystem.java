package competition.subsystems.drive;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.log4j.Logger;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class DriveSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);

    public final XCANTalon leftMaster;
    public final XCANTalon leftFollower1;
    public final XCANTalon leftFollower2;
    public final XCANTalon rightMaster;
    public final XCANTalon rightFollower1;
    public final XCANTalon rightFollower2;

    double voltageLimit = 12.0;

    @Inject
    public DriveSubsystem(CommonLibFactory factory, XPropertyManager propManager) {
        log.info("Creating DriveSubsystem");

        this.leftMaster = factory.createCANTalon(33);
        this.leftFollower1 = factory.createCANTalon(34);
        this.leftFollower2 = factory.createCANTalon(32);

        this.rightMaster = factory.createCANTalon(22);
        this.rightFollower1 = factory.createCANTalon(21);
        this.rightFollower2 = factory.createCANTalon(23);

        leftMaster.configureAsMasterMotor(this.getName(), "leftMaster", false, false);
        rightMaster.configureAsMasterMotor(this.getName(), "rightMaster", true, true);

        leftFollower1.configureAsFollowerMotor(leftMaster, false);
        leftFollower2.configureAsFollowerMotor(leftMaster, false);

        rightFollower1.configureAsFollowerMotor(rightMaster, true);
        rightFollower2.configureAsFollowerMotor(rightMaster, true);
    }

    public void tankDrive(double leftPower, double rightPower) {
        this.leftMaster.simpleSet(leftPower);
        this.rightMaster.simpleSet(rightPower);
    }

    public void enableVoltageControl() {
        this.leftMaster.configVoltageCompSaturation(voltageLimit, 0);
        this.rightMaster.configVoltageCompSaturation(voltageLimit, 0);
    }

    public void setVolts(double leftVolts, double rightVolts) {
        tankDrive(leftVolts/voltageLimit, rightVolts/voltageLimit);
    }
// y("leftDriveTicksPer5Feet", 12348.8);
// 1029.066666666667 is ticks per inch
    double pulsesPerInch = 205.81;

    public double getLeftTotalInches() {
        return leftMaster.getSelectedSensorPosition(0) / pulsesPerInch;
    }

    public double getLeftInchesPerSecond() {
        return leftMaster.getSelectedSensorVelocity(0) / pulsesPerInch * 10;
    }

    public double getRightTotalInches() {
        return rightMaster.getSelectedSensorPosition(0) / pulsesPerInch;
    }

    public double  getRightInchesPerSecond() {
        return rightMaster.getSelectedSensorVelocity(0) / pulsesPerInch * 10;
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(getLeftInchesPerSecond(), getRightInchesPerSecond());
    }

    public void resetEncoders() {
        leftMaster.setSelectedSensorPosition(0, 0, 0);
        rightMaster.setSelectedSensorPosition(0, 0, 0);
    }
}
