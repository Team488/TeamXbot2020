package competition.subsystems.climber;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;


 
@Singleton
public class ClimberSubsystem extends BaseSubsystem {

    double power;
    double minPower = 0.1;
    final DoubleProperty climberPowerProp;
    final DoubleProperty extendClimberHeightProp;
    final DoubleProperty retractClimberHeightProp;
    public XCANTalon climberMotor;
    public XCANSparkMax leader;
    private XCANSparkMax follower;
    final IdealElectricalContract contract;
    public final XSolenoid climbSolenoid;

    @Inject
    public ClimberSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ClimberSubsystem");
        pf.setPrefix(this);
        
        climberPowerProp = pf.createPersistentProperty("Climber Power", 1);
        extendClimberHeightProp = pf.createPersistentProperty("Extend Climber Height", 1);    
        retractClimberHeightProp = pf.createPersistentProperty("Retract Climber Height", -1); 
        
        this.climbSolenoid = factory.createSolenoid(contract.getClimbSolenoid().channel);
        this.contract = contract;
        
        if (contract.isClimberReady()) {
            this.leader = factory.createCANSparkMax(contract.climberMotorMaster().channel, this.getPrefix(), "ClimberMaster");
            this.follower = factory.createCANSparkMax(contract.climberMotorFollower().channel, this.getPrefix(), "ClimberFollower");
            follower.follow(leader, true);
        }
    }

    public void extendClimber () {
        setPower(extendClimberHeightProp.get());
    }

    public void retractClimber () {
        setPower(retractClimberHeightProp.get());
    }

    public void grabClamp () {
        setPower(climberPowerProp.get());
    }

    public void releaseClamp () {
        setPower(-1);
    }

    public void setPower (double power) {
        if(Math.abs(power) <= minPower){
            power = 0;
        }
        
        if(contract.isClimberReady()) {
            leader.set(power);
            autoBrake(power);
        }
    }

    public double getPower() {
        return leader.get();
    }

    public void stop () {
        setPower(0);
    }

    public void autoBrake(double power){
        if(Math.abs(power) >= minPower){
            climbSolenoid.setOn(true);
        }
        else{
            climbSolenoid.setOn(false);
        }
    }
}