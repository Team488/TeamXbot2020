package competition.subsystems.hood.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.hood.HoodSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class MaintainerHoodCommand extends BaseCommand{

    final OperatorInterface oi;
    final HoodSubsystem hood;
    final PIDManager anglePid;
    final PIDManager velocityPid;
    final DoubleProperty maxVelocityProp;
    final DoubleProperty maxThrottleProp;
    final DoubleProperty currentTickGoal;
    final DoubleProperty currentPower;
    double throttle;

    double previousTickAngle;
    double previousTime;

    @Inject
    public MaintainerHoodCommand(OperatorInterface oi, HoodSubsystem hood, PIDFactory PIDFactory, PropertyFactory propFactory, CommonLibFactory clf){
        this.oi = oi;
        this.hood = hood;
        this.anglePid = PIDFactory.createPIDManager("AnglePID");
        this.velocityPid = PIDFactory.createPIDManager("velocityPID");
        this.addRequirements(this.hood);

        propFactory.setPrefix(this.getPrefix());
        maxVelocityProp = propFactory.createPersistentProperty("Max Velocity", 10);
        maxThrottleProp = propFactory.createPersistentProperty("Max Throttle", 1);
        currentTickGoal = propFactory.createEphemeralProperty("Current Tick Goal", 0);
        currentPower = propFactory.createEphemeralProperty("Current Power", 0);
    }

    @Override
    public void initialize(){

    }

    public void execute(){

    }

}