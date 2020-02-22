package competition.subsystems.climber.commands;

import com.google.inject.Inject;

import competition.IdealElectricalContract;
import competition.operator_interface.OperatorInterface;
import competition.subsystems.climber.ClimberSubsystem;
import xbot.common.command.BaseCommand;
// import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.CommonLibFactory;

public class AutoBrakeClimberCommand extends BaseCommand{

    // public final XSolenoid climbSolenoid;
    final ClimberSubsystem climbSubsystem;
    final OperatorInterface oi;

    @Inject
    public AutoBrakeClimberCommand(OperatorInterface oi, ClimberSubsystem climbSubsystem){ // CommonLibFactory factory, IdealElectricalContract contract
        this.oi = oi;
        this.climbSubsystem = climbSubsystem;
        // this.climbSolenoid = factory.createSolenoid(contract.getClimbSolenoid().channel);
        this.addRequirements(this.climbSubsystem);

    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        climbSubsystem.autoBrake;
    }

}