package competition.subsystems.shooterwheel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revrobotics.ControlType;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;

import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class ShooterWheelSubsystem extends BaseSubsystem {
    
    final DoubleProperty targetRpmProp;
    final DoubleProperty currentRpmProp;
    public XCANSparkMax leader;
    public XCANSparkMax follower;
    IdealElectricalContract contract;
    
    @Inject
    public ShooterWheelSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        this.contract = contract;
        targetRpmProp = pf.createEphemeralProperty("TargetRPM", 0);
        currentRpmProp = pf.createEphemeralProperty("CurrentRPM", 0);

        if(contract.isShooterWheelReady()){
            this.leader = factory.createCANSparkMax(contract.shooterMotorMaster().channel, this.getPrefix(), "ShooterMaster");
            this.follower = factory.createCANSparkMax(contract.shooterMotorFollower().channel, this.getPrefix(), "ShooterFollower");
            follower.follow(leader, true);
        }
    }

    public double getCurrentSpeed(){
        return currentRpmProp.get();
    }

    public void setTargetSpeed(double speed) {
        targetRpmProp.set(speed);
    }

    public double getTargetSpeed() {
        return targetRpmProp.get();
    }

    public void changeTargetSpeed(double amount) {
        double speed = getTargetSpeed();
        speed += amount;
        setTargetSpeed(speed);
    }

    public void setPidGoal(double speed) {
        if(contract.isShooterWheelReady())
        {
            leader.setReference(speed, ControlType.kVelocity);
        }
    }


    public void setPower(double power) {
        if(contract.isShooterWheelReady())
        {
            leader.set(power);
        }
    }
  
    public void stop () {
        setPower(0);
    }

    public void periodic() {
        leader.periodic();
        currentRpmProp.set(leader.getVelocity());
    }
}