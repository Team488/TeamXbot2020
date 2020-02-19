package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseSetpointCommand;

public class PointTurretToFieldOrientedHeadingCommand extends BaseSetpointCommand {

    final TurretSubsystem turret;
    private double fieldOrientedHeadingGoal;

    @Inject
    public PointTurretToFieldOrientedHeadingCommand(TurretSubsystem turret) {
        super(turret);
        this.turret = turret;
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    public void setFieldOrientedGoal(double goal) {
        fieldOrientedHeadingGoal = goal;
    }

    @Override
    public void execute() {
        turret.setFieldOrientedGoalAngle(fieldOrientedHeadingGoal);
    }

}