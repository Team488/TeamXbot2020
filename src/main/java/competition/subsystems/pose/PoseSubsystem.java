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

    // odometry
    final DifferentialDriveOdometry odometry;
    final DoubleProperty robotX;
    final DoubleProperty robotY;
    final DoubleProperty robotHeading;

    @Inject
    public PoseSubsystem(CommonLibFactory clf, PropertyFactory propManager, DriveSubsystem drive) {
        super(clf, propManager);
        this.drive = drive;

        odometry = new DifferentialDriveOdometry(getRotation2DGyroAngle());
        robotX = propManager.createEphemeralProperty("robotX", 0.0);
        robotY = propManager.createEphemeralProperty("robotY", 0.0);
        robotHeading = propManager.createEphemeralProperty("robotHeading", 0.0);   
        odometry.resetPosition(odometry.getPoseMeters(), getRotation2DGyroAngle());
    }


    @Override
    protected double getLeftDriveDistance() {
        return drive.getLeftTotalDistance();
    }

    @Override
    protected double getRightDriveDistance() {
        return drive.getRightTotalDistance();
    }

    public Rotation2d getRotation2DGyroAngle() {
        return Rotation2d.fromDegrees(getCurrentHeading().getValue());
    }

    @Override
    public void periodic() {
        super.periodic();
        odometry.update(getRotation2DGyroAngle(), Units.inchesToMeters(getLeftDriveDistance()), Units.inchesToMeters(getRightDriveDistance()));

        // updates double property w/ odometry values
        robotX.set(Units.metersToInches(odometry.getPoseMeters().getTranslation().getX()));
        robotY.set(Units.metersToInches(odometry.getPoseMeters().getTranslation().getY()));
        robotHeading.set(odometry.getPoseMeters().getRotation().getDegrees());   
    }

}