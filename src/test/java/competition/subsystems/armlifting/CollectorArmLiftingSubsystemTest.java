package competition.subsystems.armlifting;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.armlifting.CollectorArmLiftingSubsystem;

public class CollectorArmLiftingSubsystemTest extends BaseCompetitionTest {
    @Test
    public void testCollectorArmLiftingUp() {
        CollectorArmLiftingSubsystem collectArmLifting = this.injector.getInstance(CollectorArmLiftingSubsystem.class);
        collectArmLifting.up();
        assertEquals(1, collectArmLifting.liftingCollectorArmMotor.getMotorOutputPercent(), 0.001);
    }
    @Test
    public void testCollectorArmLiftingFrozen() {
        CollectorArmLiftingSubsystem collectArmLifting = this.injector.getInstance(CollectorArmLiftingSubsystem.class);
        collectArmLifting.frozen();
        assertEquals(0, collectArmLifting.liftingCollectorArmMotor.getMotorOutputPercent(), 0.001);
    }

    @Test
    public void testCollectorArmLiftingIsAtMaximum() {
        CollectorArmLiftingSubsystem collectArmLifting = this.injector.getInstance(CollectorArmLiftingSubsystem.class);
        collectArmLifting.setCurrentArmLiftHeight(1);
        collectArmLifting.setPower(1);
        assertEquals(0, collectArmLifting.liftingCollectorArmMotor.getMotorOutputPercent(), 0.001);
    }
    
    @Test
    public void testCollectorArmLiftingIsAtMinimum() {
        CollectorArmLiftingSubsystem collectArmLifting = this.injector.getInstance(CollectorArmLiftingSubsystem.class);
        collectArmLifting.setCurrentArmLiftHeight(-1);
        collectArmLifting.setPower(-1);
        assertEquals(0, collectArmLifting.liftingCollectorArmMotor.getMotorOutputPercent(), 0.001);

    }

    @Test
    public void testCollectorArmLiftingStop() {
        CollectorArmLiftingSubsystem collectArmLifting = this.injector.getInstance(CollectorArmLiftingSubsystem.class);
        collectArmLifting.up();
        assertEquals(1, collectArmLifting.liftingCollectorArmMotor.getMotorOutputPercent(), 0.001);
        if(collectArmLifting.liftingCollectorArmMotor.getMotorOutputPercent() > 0) {
            collectArmLifting.stop();
        }
        
        assertEquals(0, collectArmLifting.liftingCollectorArmMotor.getMotorOutputPercent(), 0.001);
    }
}