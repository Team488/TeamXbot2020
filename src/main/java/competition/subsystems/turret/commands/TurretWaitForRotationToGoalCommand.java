package competition.subsystems.turret.commands;

import com.google.inject.Inject;
import competition.subsystems.turret.TurretSubsystem;
import xbot.common.command.BaseWaitForMaintainerCommand;
import xbot.common.properties.PropertyFactory;

public class TurretWaitForRotationToGoalCommand extends BaseWaitForMaintainerCommand {

    @Inject
    public TurretWaitForRotationToGoalCommand(TurretSubsystem tSubsystem, PropertyFactory pf) {
        super(tSubsystem, pf, 1);
    }
    
}