package competition.subsystems.turret.commands;

import com.google.inject.Inject;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.command.BaseSetpointCommand;

public class TurretRotateToVisionTargetCommand extends BaseSetpointCommand
{
    private final TurretSubsystem turretSubsystem;
    private final VisionSubsystem visionSubsystem;

    @Inject
    public TurretRotateToVisionTargetCommand(TurretSubsystem tSubsystem, VisionSubsystem vSubsystem) {
        super(tSubsystem);

        this.turretSubsystem = tSubsystem;
        this.visionSubsystem = vSubsystem;
    }

    @Override
    public void initialize()
    {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        // Check if vision subsystem is active and our turret is calibrated
        if (this.visionSubsystem.isAmbanActive() && this.visionSubsystem.isAmbanFixAcquired()) {
            // yawToTarget may ask us to go beyond our rotation limit so we need to correct for this
            double yawToTarget = this.visionSubsystem.getAmbanYawToTarget();
            this.turretSubsystem.setGoalAngle(calculateTargetAngle(yawToTarget));
        } else {
            this.turretSubsystem.setGoalAngle(this.turretSubsystem.getCurrentAngle());
        }
    }
    
    public double calculateTargetAngle(double yawToTarget) {
        double targetAngle = this.turretSubsystem.getCurrentAngle() + yawToTarget;

        // Check if we need to turn in the opposite direction due to limits
        if (targetAngle > this.turretSubsystem.getMaxAngle())
        {
            targetAngle -= 360;
        } else if (targetAngle < this.turretSubsystem.getMinAngle()) {
            targetAngle += 360;
        }

        return targetAngle;
    }
}