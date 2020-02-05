package competition.subsystems.turret;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;

public class TurretSubsystemTest extends BaseCompetitionTest {

    @Test
    public void testStop() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        turret.stop();

        assertEquals(0, turret.motor.getMotorOutputPercent(), 0.001);
    }

}