package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.properties.PropertyFactory;

public class TurretMaintainerCommand extends BaseCommand {

    final TurretSubsystem turret;
    final PIDManager pid;

    @Inject
    public TurretMaintainerCommand(TurretSubsystem turret, PropertyFactory pf, PIDFactory pidf) {
        this.turret = turret;

        pid = pidf.createPIDManager("TurretPID", 0.04, 0, 0);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        double power = 0;
        if (turret.isCalibrated()){
            power = pid.calculate(turret.getGoalAngle(), turret.getCurrentAngle());
        }

        turret.setPower(power);
    }
}