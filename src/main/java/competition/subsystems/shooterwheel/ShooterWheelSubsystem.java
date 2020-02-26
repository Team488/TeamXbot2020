package competition.subsystems.shooterwheel;

// import javax.swing.text.Utilities;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revrobotics.ControlType;

import competition.IdealElectricalContract;
// import edu.wpi.first.wpilibj.Timer;
import xbot.common.command.BaseSetpointSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class ShooterWheelSubsystem extends BaseSetpointSubsystem {
    
    private final DoubleProperty targetRpmProp;
    private final DoubleProperty currentRpmProp;
    // private final DoubleProperty timerProp;

    public XCANSparkMax leader;
    private XCANSparkMax follower;
    IdealElectricalContract contract;
    // private Timer timer;
    
    @Inject
    public ShooterWheelSubsystem(final CommonLibFactory factory, final PropertyFactory pf,
            final IdealElectricalContract contract) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        this.contract = contract;

        targetRpmProp = pf.createEphemeralProperty("TargetRPM", 0);
        currentRpmProp = pf.createEphemeralProperty("CurrentRPM", 0);
        // timerProp = pf.createPersistentProperty("Timer", 3);

        if (contract.isShooterWheelReady()) {
            this.leader = factory.createCANSparkMax(contract.shooterMotorMaster().channel, this.getPrefix(),
                    "ShooterMaster");
            this.follower = factory.createCANSparkMax(contract.shooterMotorFollower().channel, this.getPrefix(),
                    "ShooterFollower");
            follower.follow(leader, true);
        }
    }

    public void setTargetRPM(final double speed) {
        targetRpmProp.set(speed);
    }

    public double getTargetRPM() {
        return targetRpmProp.get();
    }

    public void changeTargetRPM(final double amount) {
        double speed = getTargetRPM();
        speed += amount;
        setTargetRPM(speed);
    }

    public void setPidSetpoint(final double speed) {
        if (contract.isShooterWheelReady()) {
            leader.setReference(speed, ControlType.kVelocity);
        }
    }

    public void setPower(final double power) {
        if (contract.isShooterWheelReady()) {
            leader.set(power);
        }
    }

    public double getPower() {
        return leader.get();
    }

    public void stop() {
        setPower(0);
    }

    public double getCurrentRPM() {
        return leader.getVelocity();
    }

    public void periodic() {
        leader.periodic();
        follower.periodic();
        currentRpmProp.set(getCurrentRPM());
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
    public void setTargetValue(final double value) {
        setTargetRPM(value);
    }

    // public void setTimer(double seconds) {
    //     timerProp.set(seconds);
    // }

    // // public void timedShooting() {
    // //     double time = timerProp.get();
    // //     timer.start();
    // //     if (timer.get() <= time) {
    // //         setPower(1);
    // //     }
    // //     timer.stop();
    // //     setPower(0);
    // // }
}