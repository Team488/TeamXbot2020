package competition.subsystems.turret.commands;

import com.google.inject.Inject;
import competition.subsystems.turret.TurretSubsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import xbot.common.command.BaseCommand;
import xbot.common.logging.LoggingLatch;
import xbot.common.logic.Latch.EdgeType;

public class TurretRotateToVisionTargetCommand extends BaseCommand
{
    final TurretSubsystem turretSubsystem;
    final NetworkTable ambanNetworkTable;
    final LoggingLatch fixAquiredLogLatch;
    final LoggingLatch fixLostLogLatch;

    @Inject
    public TurretRotateToVisionTargetCommand(TurretSubsystem tSubsystem, NetworkTableInstance networkTableInstance) {
        this.turretSubsystem = tSubsystem;
        this.addRequirements(this.turretSubsystem);
        this.ambanNetworkTable = networkTableInstance.getTable("amban");
        this.fixAquiredLogLatch = new LoggingLatch("AmbanFixAquired", "Amban fix acquired", EdgeType.RisingEdge);
        this.fixLostLogLatch = new LoggingLatch("AmbanFixLost", "Amban fix lost", EdgeType.FallingEdge);
    }

    @Override
    public void initialize()
    {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        // Check if vision subsystem is active and our turret is calibrated
        if (isAmbanActive() && isTargetLocked()) {
            this.fixAquiredLogLatch.checkValue(true);
            this.fixLostLogLatch.checkValue(true);

            // yawToTarget may ask us to go beyond our rotation limit so we need to correct for this
            double yawToTarget = getYawToTarget();
            this.turretSubsystem.setGoalAngle(calculateTargetAngle(yawToTarget));
        } else {
            this.fixAquiredLogLatch.checkValue(false);
            this.fixLostLogLatch.checkValue(false);
            
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

    public double getYawToTarget() {
        return this.ambanNetworkTable.getEntry("yawToTarget").getNumber(0).doubleValue();
    }

    public boolean isAmbanActive() {
        return this.ambanNetworkTable.getEntry("active").getBoolean(false);
    }

    public boolean isTargetLocked() {
        return this.ambanNetworkTable.getEntry("fixAcquired").getBoolean(false);
    }
}