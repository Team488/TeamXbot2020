package competition.subsystems.drive;

import static org.junit.Assert.assertEquals;
import org.junit.Test;
import xbot.common.injection.BaseWPITest;

public class KinematicsDriveTest extends BaseWPITest {   
    @Test
    public void testKinematicsDrive() {
        DriveSubsystem driveSubsystem = this.injector.getInstance(DriveSubsystem.class);
        
        double joystickLeftPower = 1;
        double joystickRightPower = 1;

        double linearVelocityConstant = 172; // constants
        double angularVelocityConstant = 0; // constants
        
        driveSubsystem.kinematicsDrive(joystickLeftPower * linearVelocityConstant, joystickRightPower * angularVelocityConstant);

        assertEquals(1, driveSubsystem.leftMaster.get(), 0.01);
        assertEquals(1, driveSubsystem.rightMaster.get(), 0.01);
    }
}
