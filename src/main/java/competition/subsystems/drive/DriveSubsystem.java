package competition.subsystems.drive;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import org.apache.log4j.Logger;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.XPropertyManager;

@Singleton
public class DriveSubsystem extends BaseSubsystem {
    private static Logger log = Logger.getLogger(DriveSubsystem.class);

    public final XCANTalon leftMaster;
    public final XCANTalon leftFollower;
    public final XCANTalon rightMaster;
    public final XCANTalon rightFollower;

    double voltageLimit = 12.0;

    @Inject
    public DriveSubsystem(CommonLibFactory factory, XPropertyManager propManager) {
        log.info("Creating DriveSubsystem");

        this.leftMaster = factory.createCANTalon(34);
        this.leftFollower = factory.createCANTalon(35);
        this.rightMaster = factory.createCANTalon(21);
        this.rightFollower = factory.createCANTalon(20);

        XCANTalon.configureMotorTeam("LeftDrive", "LeftMaster", leftMaster, leftFollower, 
        true, true, false);
        XCANTalon.configureMotorTeam("RightDrive", "RightMaster", rightMaster, rightFollower, 
        false, false, false);
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
    double pulsesPerInch = 1029.06;

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
}
