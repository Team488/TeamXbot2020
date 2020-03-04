package competition.subsystems.turret.commands;

import com.google.inject.Inject;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.vision.VisionSubsystem;
import xbot.common.command.BaseSetpointCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class TurretRotateToVisionTargetCommand extends BaseSetpointCommand
{
    private final TurretSubsystem turretSubsystem;
    private final VisionSubsystem visionSubsystem;

    private final DoubleProperty retargetingThresholdProp;

    @Inject
    public TurretRotateToVisionTargetCommand(TurretSubsystem tSubsystem, VisionSubsystem vSubsystem, PropertyFactory pf) {
        super(tSubsystem);

        pf.setPrefix(this);
        retargetingThresholdProp = pf.createPersistentProperty("Retargeting threshold degrees", 15);


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
        if (!this.turretSubsystem.isMaintainerAtGoal()) {
            double distanceFromGoal = Math.abs(this.turretSubsystem.getCurrentAngle() - this.turretSubsystem.getGoalAngle());
            if (distanceFromGoal > this.retargetingThresholdProp.get()) {
                // We are still moving to our goal.
                // This is required because we don't want amban to pick up a target on
                // the opposite side of the field while we are doing a full rotation to
                // cross the deadband.
                return;
            }
        }

        // Check if vision subsystem is active and our turret is calibrated
        if (this.visionSubsystem.isAmbanActive() && this.visionSubsystem.isAmbanFixAcquired()) {
            // yawToTarget may ask us to go beyond our rotation limit so we need to correct for this
            double yawToTarget = this.visionSubsystem.getAmbanYawToTarget();
            this.turretSubsystem.setGoalAngle(this.turretSubsystem.getCurrentAngle() + yawToTarget);
        } else {
            this.turretSubsystem.setGoalAngle(this.turretSubsystem.getCurrentAngle());
        }
    }
}