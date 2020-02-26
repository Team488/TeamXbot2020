package competition.subsystems.shooterwheel;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.shooterwheel.commands.BestShotAdjustment;

public class ShotAdjustmentTest extends BaseCompetitionTest {
    
    @Test
    public void testNumbers() {
        BestShotAdjustment command = injector.getInstance(BestShotAdjustment.class);
        command.initialize();
        command.findBestShot(10);
        //TODO: do math to find if given an angle what velosity should it be
        assertEquals(1.0, 1.0, 0.1);
    }
}