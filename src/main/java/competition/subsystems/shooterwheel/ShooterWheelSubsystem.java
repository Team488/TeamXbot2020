package competition.subsystems.shooterwheel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revrobotics.ControlType;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;

import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
import xbot.common.math.PIDManager; // maybe using PID in subsystem


@Singleton
public class ShooterWheelSubsystem extends BaseSubsystem {
    
    PIDManager pid; // maybe using PID in subystem for voltage tuning/compensation
    public double neededVolt; // might be needed
    public double D;
    public double FF; // Feed Forward
    public double speed;
    public double currentV;
    //-------------Fields above are subject to change----------------------------
    final DoubleProperty targetRpmProp;
    final BooleanProperty speedWithinToleranceProp;
    final DoubleProperty speedToleranceProp;
    final DoubleProperty currentRpmProp;
    public XCANSparkMax leader;
    private XCANSparkMax follower;
    IdealElectricalContract contract;
    
    @Inject
    public ShooterWheelSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        this.contract = contract;
        speedToleranceProp = pf.createPersistentProperty("speedTolerance", 100);
        speedWithinToleranceProp = pf.createEphemeralProperty("speedWithinTolerance", false);
        targetRpmProp = pf.createEphemeralProperty("TargetRPM", 0);
        currentRpmProp = pf.createEphemeralProperty("CurrentRPM", 0);

        if(contract.isShooterWheelReady()){
            this.leader = factory.createCANSparkMax(contract.shooterMotorMaster().channel, this.getPrefix(), "ShooterMaster");
            this.follower = factory.createCANSparkMax(contract.shooterMotorFollower().channel, this.getPrefix(), "ShooterFollower");
            follower.follow(leader, true);
        }
    }

    public void enableVoltCompenstation(){ // not done here
        double nomialVolt = leader.getVoltageCompensationNominalVoltage();
        leader.enableVoltageCompensation(nomialVolt);
        // neededVolt = RPM
    }   

    public void setTargetRPM(double speed) {
        targetRpmProp.set(speed);
    }

    public double getTargetRPM() {
        return targetRpmProp.get();
    }

    public void changeTargetRPM(double amount) {
        double speed = getTargetRPM();
        speed += amount;
        setTargetRPM(speed);
    }

    public void setPidSetpoint(double speed) {
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

    public double getCurrentRPM() {
        return leader.getVelocity();
    }

    public void periodic() { // runs multiple times
        leader.periodic();
        follower.periodic();
        currentRpmProp.set(getCurrentRPM());
        speedWithinToleranceProp.set(getShooterWheelAtTargetSpeed());
    }

    public boolean getShooterWheelAtTargetSpeed() {
        if (Math.abs(currentRpmProp.get() - targetRpmProp.get()) < speedToleranceProp.get()) {
            return true;
        }
        return false;
    }

    public void resetPID() {
        leader.setIAccum(0);
    }

    public void resetWheel() {
        setPower(0);
        setTargetRPM(0);
        resetPID();
    }

    public void setCurrentLimits() {
        leader.setSmartCurrentLimit(40);
        leader.setClosedLoopRampRate(0.01);
    }

    public void configurePID() {
        leader.setIMaxAccum(1, 0);
    }
}