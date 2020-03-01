package competition.subsystems.shooterwheel;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revrobotics.ControlType;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSetpointSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class ShooterWheelSubsystem extends BaseSetpointSubsystem {
    
    private final DoubleProperty targetRpmProp;
    private final DoubleProperty currentRpmProp;
    private final DoubleProperty rpmTrimProp;

    public XCANSparkMax leader;
    private XCANSparkMax follower;
    IdealElectricalContract contract;

    @Inject
    public ShooterWheelSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        this.contract = contract;

        targetRpmProp = pf.createEphemeralProperty("TargetRPM", 0);
        currentRpmProp = pf.createEphemeralProperty("CurrentRPM", 0);
        rpmTrimProp = pf.createEphemeralProperty("TrimRPM", 0);

        if (contract.isShooterWheelReady()) {
            this.leader = factory.createCANSparkMax(contract.shooterMotorMaster().channel, this.getPrefix(),
                    "ShooterMaster");
            this.follower = factory.createCANSparkMax(contract.shooterMotorFollower().channel, this.getPrefix(),
                    "ShooterFollower");
            follower.follow(leader, true);
        }
    }

    public void setTargetRPM(double speed) {
        targetRpmProp.set(speed);
    }

    public double getTargetRPM() {
        if (Math.abs(targetRpmProp.get()) < 50.0) {
            return targetRpmProp.get();
        }
        return targetRpmProp.get() + getTrimRPM();
    }

    public void changeTrimRPM(double changeRate) {
        rpmTrimProp.set(getTrimRPM() + changeRate);
    }
    
    public void setTargetTrimRPM(double trim) {
        rpmTrimProp.set(trim);
    }

    public double getTrimRPM() {
        return rpmTrimProp.get();
    }
    

    public void resetTrimRPM() {
        rpmTrimProp.set(0);
    }

    public void changeTargetRPM(double amount) {
        double speed = getTargetRPM();
        speed += amount;
        setTargetRPM(speed);
    }

    public void setPidSetpoint(double speed) {
        if (contract.isShooterWheelReady()) {
            leader.setReference(speed, ControlType.kVelocity);
        }
    }

    public void setPower(double power) {
        if (contract.isShooterWheelReady()) {
            leader.set(power);
        }
    }

    public double getPower() {
        if (contract.isShooterWheelReady()) {
            return leader.get();
        }
        return 0;
    }

    public void stop() {
        setPower(0);
    }

    public double getCurrentRPM() {
        if (contract.isShooterWheelReady()) {
            return leader.getVelocity();
        }
        return 0;
    }

    public void periodic() {
        if (contract.isShooterWheelReady()) {
            leader.periodic();
            follower.periodic();
            currentRpmProp.set(getCurrentRPM());
        }
    }

    public void resetPID() {
        if (contract.isShooterWheelReady()) {
            leader.setIAccum(0);
        }
    }

    public void resetWheel() {
        setPower(0);
        setTargetRPM(0);
        resetPID();
    }

    public void setCurrentLimits() {
        if (contract.isShooterWheelReady()) {
            leader.setSmartCurrentLimit(40);
            leader.setClosedLoopRampRate(0.01);
        }
    }

    public void configurePID() {
        if (contract.isShooterWheelReady()) {
            leader.setIMaxAccum(1, 0);
        }
    }

    @Override
    public double getCurrentValue() {
        return getCurrentRPM();
    }

    @Override
    public double getTargetValue() {
        return getTargetRPM();
    }

    @Override
    public boolean isCalibrated() {
        return true;
    }

    @Override
    public void setTargetValue(double value) {
        setTargetRPM(value);
    }

    @Override
    public boolean isMaintainerAtGoal() {
        boolean basicAtGoal =  super.isMaintainerAtGoal();
        return basicAtGoal && shooterMovingSome();
    }

    private boolean shooterMovingSome() {
        return getCurrentRPM() > 500;
    }
}