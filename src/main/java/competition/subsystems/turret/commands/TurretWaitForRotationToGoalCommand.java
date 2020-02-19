package competition.subsystems.turret.commands;

import com.google.inject.Inject;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.controls.sensors.XTimer;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class TurretWaitForRotationToGoalCommand extends BaseCommand {

    private final TurretSubsystem turretSubsystem;
    private final DoubleProperty timeoutProperty;

    private double startTime = -1;

    @Inject
    public TurretWaitForRotationToGoalCommand(PropertyFactory pf, TurretSubsystem tSubsystem) {
        this.turretSubsystem = tSubsystem;

        pf.setPrefix(this);

        this.timeoutProperty = pf.createPersistentProperty("Timeout", 1);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        this.startTime = XTimer.getFPGATimestamp();
    }

    @Override
    public void execute() {
        // This command doesn't do anything
    }

    @Override
    public boolean isFinished() {
        return this.turretSubsystem.isMaintainerAtGoal() || XTimer.getFPGATimestamp() - this.startTime > this.timeoutProperty.get();
    }
    
}