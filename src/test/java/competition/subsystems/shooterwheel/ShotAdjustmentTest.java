package competition.subsystems.shooterwheel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.shooterwheel.commands.BestShotAdjustment;

public class ShotAdjustmentTest extends BaseCompetitionTest {
    
    @Test
    public void rightWay(){
        BestShotAdjustment best = injector.getInstance(BestShotAdjustment.class);
        best.initialize();
        best.findBestShot(10);

        assertTrue(best.bestAngle > 0 && best.bestAngle < Math.PI/2);
    }

    @Test
    public void positiveVelocity(){
        BestShotAdjustment best = injector.getInstance(BestShotAdjustment.class);
        best.initialize();
        best.findBestShot(10);

        assertTrue(best.bestVelocity > 0);
    }
    
    @Test
    public void testAngleNumbers() {
        BestShotAdjustment best = injector.getInstance(BestShotAdjustment.class);
        best.initialize();
        best.findBestShot(10);
        assertEquals(0.876058050598 , best.bestAngle, .001);
    }

    @Test
    public void testVelocityNumbers() {
        BestShotAdjustment best = injector.getInstance(BestShotAdjustment.class);
        best.initialize();
        best.findBestShot(10);
        assertEquals(25.5081686263 , best.bestVelocity, .001);
    }
}