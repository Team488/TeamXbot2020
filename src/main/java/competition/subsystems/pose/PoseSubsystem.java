package competition.subsystems.pose;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.drive.DriveSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.subsystems.pose.BasePoseSubsystem;

@Singleton
public class PoseSubsystem extends BasePoseSubsystem {

    final DriveSubsystem drive;
    final DoubleProperty intPosX;
    final DoubleProperty intPosY;

    @Inject
    public PoseSubsystem(CommonLibFactory clf, PropertyFactory propManager, DriveSubsystem drive, PropertyFactory pf) {
        super(clf, propManager);

        this.drive = drive;
        intPosX = pf.createEphemeralProperty("intPosX", 0);
        intPosY = pf.createEphemeralProperty("intPosY", 0);

    }


    @Override
    protected double getLeftDriveDistance() {
        return drive.getLeftTotalDistance();
    }

    @Override
    protected double getRightDriveDistance() {
        return drive.getRightTotalDistance();
    }

    @Override
    public double getIntPosX() {

    }
}