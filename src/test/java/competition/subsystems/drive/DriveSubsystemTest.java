package competition.subsystems.drive;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import xbot.common.injection.BaseWPITest;

public class DriveSubsystemTest extends BaseWPITest {
    @Test
    public void testTankDrive() {
        DriveSubsystem driveSubsystem = this.injector.getInstance(DriveSubsystem.class);
        driveSubsystem.tankDrive(1, 1);

        assertEquals(1, driveSubsystem.leftMaster.get() , 0.001);
        assertEquals(1, driveSubsystem.rightMaster.get(), 0.001);
    }

    @Test
    public void testDriveBackward() {
        DriveSubsystem drive = this.injector.getInstance(DriveSubsystem.class);
        drive.driveBackward(1);

        assertEquals(-1, drive.leftMaster.get(), 0.001);
        assertEquals(-1, drive.rightMaster.get(), 0.001);
    }
}
