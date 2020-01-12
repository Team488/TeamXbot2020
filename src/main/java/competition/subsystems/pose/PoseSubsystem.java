package competition.subsystems.pose;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.pose.BasePoseSubsystem;

@Singleton
public class PoseSubsystem extends BasePoseSubsystem {

    DifferentialDriveOdometry fancyOdometry;
    DriveSubsystem drive;

    @Inject
    public PoseSubsystem(CommonLibFactory clf, PropertyFactory propManager, DriveSubsystem drive) {
        super(clf, propManager);
        this.drive = drive;
        fancyOdometry = new DifferentialDriveOdometry(new Rotation2d(Math.toRadians(90)));
    }


    @Override
    protected double getLeftDriveDistance() {
        return drive.getLeftTotalInches();
    }

    @Override
    protected double getRightDriveDistance() {
        return drive.getRightTotalInches();
    }

    @Override
    public void updatePeriodicData() {
        super.updatePeriodicData();

        // Now, also perform the fancy new odometry in WPILIB2020
        fancyOdometry.update(
            new Rotation2d(Math.toRadians(getCurrentHeading().getValue())),
            getLeftDriveDistance(),
            getRightDriveDistance());
    }

    public Pose2d getFancyPose() {
        return fancyOdometry.getPoseMeters();
    }

}