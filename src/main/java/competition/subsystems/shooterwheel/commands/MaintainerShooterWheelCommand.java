package competition.subsystems.shooterwheel.commands;
import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.PIDFactory;
import xbot.common.math.PIDManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class MaintainerShooterWheelCommand extends BaseCommand {

    final OperatorInterface oi;
    final ShooterWheelSubsystem shooterWheelSubsystem;
    final PIDManager velocityPid;
    final DoubleProperty maximumVelocityProp;
    final DoubleProperty maximumThrottlePower;
    final DoubleProperty currentTickGoal;
    final DoubleProperty currentPower;

    double throttle;
    double previousTime;
    

    @Inject
    public MaintainerShooterWheelCommand(OperatorInterface oi, ShooterWheelSubsystem shooterWheelSubsystem, PIDFactory pidFactory, PropertyFactory propFactory, CommonLibFactory clf){
        this.oi = oi;
        this.shooterWheelSubsystem = shooterWheelSubsystem;
        this.velocityPid = pidFactory.createPIDManager(getPrefix() + "VelocityPID", 0.01, 0, 0);
        this.addRequirements(this.shooterWheelSubsystem);

        propFactory.setPrefix(this.getPrefix());

        maximumVelocityProp = propFactory.createEphemeralProperty("MaximumVelocity", 100);
        maximumThrottlePower = propFactory.createEphemeralProperty("MaximumThrottle", 0.5);
        currentTickGoal = propFactory.createEphemeralProperty("CurrentTickGoal", 1);
        currentPower = propFactory.createEphemeralProperty("CurrentPower", 1);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        //hangerSubsystem.extendHanger();
    }
}