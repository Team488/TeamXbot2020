package competition.subsystems.pose;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.util.Units;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.pose.BasePoseSubsystem;

@Singleton
public class PoseSubsystem extends BasePoseSubsystem {

    final DriveSubsystem drive;

    final DifferentialDriveOdometry odometry;
    final DoubleProperty wpiRobotX;
    final DoubleProperty wpiRobotY;
    final DoubleProperty wpiRobotHeading;

    @Inject
    public PoseSubsystem(CommonLibFactory clf, PropertyFactory propManager, DriveSubsystem drive) {
        super(clf, propManager);

        this.drive = drive;

        odometry = new DifferentialDriveOdometry(getHeadingAsRotation2D());
        wpiRobotX = propManager.createEphemeralProperty("wpiPose X", 0.0);
        wpiRobotY = propManager.createEphemeralProperty("wpiPose Y", 0.0);
        wpiRobotHeading = propManager.createEphemeralProperty("wpiPose Heading", 0.0);   

        odometry.resetPosition(odometry.getPoseMeters(), getHeadingAsRotation2D());
    }


    @Override
    protected double getLeftDriveDistance() {
        return drive.getLeftTotalDistance();
    }

    @Override
    protected double getRightDriveDistance() {
        return drive.getRightTotalDistance();
    }

    public Rotation2d getHeadingAsRotation2D() {
        return Rotation2d.fromDegrees(getCurrentHeading().getValue());
    }

    @Override
    public void periodic() {
        super.periodic();
        odometry.update(getHeadingAsRotation2D(), Units.inchesToMeters(getLeftDriveDistance()), Units.inchesToMeters(getRightDriveDistance()));
        wpiRobotX.set(Units.metersToInches(odometry.getPoseMeters().getTranslation().getX()));
        wpiRobotY.set(Units.metersToInches(odometry.getPoseMeters().getTranslation().getY()));
        wpiRobotHeading.set(odometry.getPoseMeters().getRotation().getDegrees());   
    }

    @Override
    public void setCurrentPosition(double newXPosition, double newYPosition) {
        super.setCurrentPosition(newXPosition, newYPosition);
        wpiRobotX.set(newXPosition);
        wpiRobotY.set(newYPosition);
    }

}