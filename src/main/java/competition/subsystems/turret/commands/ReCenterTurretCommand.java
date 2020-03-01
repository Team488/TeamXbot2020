package competition.subsystems.turret.commands;

import com.google.inject.Inject;

import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseSetpointCommand;

public class ReCenterTurretCommand extends BaseSetpointCommand{

    TurretSubsystem turret;
    private double headingGoal = -90; // recenter value, can also be 270

    @Inject
    public ReCenterTurretCommand(TurretSubsystem turret){
        super(turret);
        this.turret = turret;
    }

    @Override
    public void initialize(){
        log.info("Initializing");
        turret.setGoalAngle(headingGoal);
    }

    @Override
    public void execute(){
    }

}