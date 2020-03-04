package competition.subsystems.turret;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
    public double expectedGoalAngle;

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

        setRawTurretAngle(turret, 0);

        turret.calibrateTurret();
        setRawTurretAngle(turret, this.initialAngle - 90);
        turret.setGoalAngle(turret.getCurrentAngle());

        double currentAngle = turret.getCurrentAngle();
        assertEquals(this.initialAngle, currentAngle, 0.001);
        assertTrue(currentAngle >= turret.getMinAngle());
        assertTrue(currentAngle <= turret.getMaxAngle());

        NetworkTable nt = networkTableInstance.getTable("amban");
        nt.getEntry("active").setBoolean(this.ambanActive);
        nt.getEntry("fixAcquired").setBoolean(this.ambanFixAcquired);
        nt.getEntry("yawToTarget").setNumber(this.ambanYawToTarget);
        
        TurretRotateToVisionTargetCommand rotateToVisionTargetCommand = this.injector.getInstance(TurretRotateToVisionTargetCommand.class);

        rotateToVisionTargetCommand.initialize();
        rotateToVisionTargetCommand.execute();

        double actualGoalAngle = turret.getGoalAngle();
        assertEquals(this.expectedGoalAngle, actualGoalAngle, 0.001);
        assertTrue(actualGoalAngle >= turret.getMinAngle());
        assertTrue(actualGoalAngle <= turret.getMaxAngle());
    }

    private void setRawTurretAngle(TurretSubsystem turret, double angle) {
        double ticks = angle / turret.getTicksPerDegree();
        ((MockCANTalon)(turret.motor)).setPosition((int)ticks);
    }
}