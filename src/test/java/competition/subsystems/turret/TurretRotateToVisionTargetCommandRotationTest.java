package competition.subsystems.turret;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.commands.TurretRotateToVisionTargetCommand;
import competition.subsystems.vision.VisionSubsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import xbot.common.controls.actuators.mock_adapters.MockCANTalon;

@RunWith(Parameterized.class)
public class TurretRotateToVisionTargetCommandRotationTest extends BaseCompetitionTest {

    @Parameter(0)
    public boolean ambanActive;

    @Parameter(1)
    public boolean ambanFixAcquired;

    @Parameter(2)
    public double initialAngle;

    @Parameter(3)
    public double ambanYawToTarget;

    @Parameter(4)
    public double expectedTarget;

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {     
            { false, false, 10, 100, 10 },
            { true, false, 10, 100, 10 },
            { true, true, 15, 180, -165 },
            { true, true, -160, -25, 175 }
        });
    }

    @Test
    public void testRotateToVisionTarget() {
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        VisionSubsystem vision = this.injector.getInstance(VisionSubsystem.class);
        NetworkTableInstance networkTableInstance = this.injector.getInstance(NetworkTableInstance.class);
        
        TurretRotateToVisionTargetCommand rotateToVisionTargetCommand =
            new TurretRotateToVisionTargetCommand(turret, vision);

        turret.calibrateTurret();
        setRawTurretAngle(turret, this.initialAngle - 90);
        turret.setGoalAngle(turret.getCurrentAngle());

        assertEquals(this.initialAngle, turret.getCurrentAngle(), 0.001);

        NetworkTable nt = networkTableInstance.getTable("amban");
        nt.getEntry("active").setBoolean(this.ambanActive);
        nt.getEntry("fixAcquired").setBoolean(this.ambanFixAcquired);
        nt.getEntry("yawToTarget").setNumber(this.ambanYawToTarget);

        rotateToVisionTargetCommand.initialize();
        rotateToVisionTargetCommand.execute();

        assertEquals(this.expectedTarget, turret.getGoalAngle(), 0.001);
    }

    private void setRawTurretAngle(TurretSubsystem turret, double angle) {
        double ticks = angle / turret.getTicksPerDegree();
        ((MockCANTalon)(turret.motor)).setPosition((int)ticks);
    }
}