package competition.subsystems.turret;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.commands.TurretMaintainerCommand;

public class TurretMaintainerCommandTest extends BaseCompetitionTest {

    TurretMaintainerCommand command;
    TurretSubsystem turret;

    @Override
    public void setUp() {
        super.setUp();
        command = injector.getInstance(TurretMaintainerCommand.class);
        turret = injector.getInstance(TurretSubsystem.class);
    }

    @Test
    public void testMaintain() {
        turret.setGoalAngle(135);
        turret.calibrateTurret();
        
        command.initialize();
        command.execute();

        assertTrue("Turret should be rotating left", turret.motor.getMotorOutputPercent() > 0.1);
    }
}