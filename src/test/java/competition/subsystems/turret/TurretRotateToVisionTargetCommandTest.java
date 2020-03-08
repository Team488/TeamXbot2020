package competition.subsystems.turret;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.commands.TurretRotateToVisionTargetCommand;
import competition.subsystems.vision.VisionSubsystem;

public class TurretRotateToVisionTargetCommandTest extends BaseCompetitionTest {

    @Test
    public void testRotateToVisionTargetWithInactiveAmban() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        VisionSubsystem vision = this.injector.getInstance(VisionSubsystem.class);

        TurretRotateToVisionTargetCommand rotateToVisionTargetCommand = this.injector.getInstance(TurretRotateToVisionTargetCommand.class);

        double initialAngle = turret.getCurrentAngle();

        rotateToVisionTargetCommand.initialize();
        rotateToVisionTargetCommand.execute();

        assertEquals(initialAngle, turret.getCurrentAngle(), 0.001);
    }
}