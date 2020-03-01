package competition.subsystems.turret;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.subsystems.turret.commands.ReCenterTurretCommand;

public class ReCenterTurretCommandTest extends BaseCompetitionTest{

    @Test
    public void checkReCenter(){
        TurretSubsystem turret = this.injector.getInstance(TurretSubsystem.class);
        ReCenterTurretCommand recenter = this.injector.getInstance(ReCenterTurretCommand.class);

        turret.setGoalAngle(35);
        recenter.initialize();
        assertTrue("Checking if Re-center angle is set", -90 == turret.getGoalAngle());
    }

}