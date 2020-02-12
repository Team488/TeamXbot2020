package competition.subsystems.armlifting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.armlifting.FrontCollectingSubsystem;

public class FrontCollectingSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testFrontCollectingIntake() {
        FrontCollectingSubsystem frontCollect = this.injector.getInstance(FrontCollectingSubsystem.class);
        frontCollect.intake();
        assertEquals(1, frontCollect.frontCollectingMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testFrontCollectingIsAtCapacity() {
        FrontCollectingSubsystem frontCollect = this.injector.getInstance(FrontCollectingSubsystem.class);
        frontCollect.setCurrentTotalBalls(6);
        frontCollect.setPower(1);
        assertEquals(0, frontCollect.frontCollectingMotor.getMotorOutputPercent(), 0.001);
    }

    
    @Test
    public void testFrontCollectingStop() {
        FrontCollectingSubsystem frontCollect = this.injector.getInstance(FrontCollectingSubsystem.class);
        frontCollect.intake();
        assertEquals(1, frontCollect.frontCollectingMotor.getMotorOutputPercent(), 0.001);
        if (frontCollect.frontCollectingMotor.getMotorOutputPercent() > 0) {
            frontCollect.stop();
        }
        
        assertEquals(0, frontCollect.frontCollectingMotor.getMotorOutputPercent(), 0.001);
    }

}