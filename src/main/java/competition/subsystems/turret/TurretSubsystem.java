package competition.subsystems.turret;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class TurretSubsystem extends BaseSubsystem {

    public XCANTalon motor;
    public double currentAngle;
    final DoubleProperty maxAngleProp;
    final DoubleProperty minAngleProp;
    final DoubleProperty turnPowerProp;
    final DoubleProperty currentAngleProp;
    final DoubleProperty offsetProp;

    @Inject
    public TurretSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract, XScheduler scheduler) {
        log.info("Creating TurretSubsystem");
        pf.setPrefix(this);

        currentAngle = 0;
        maxAngleProp = pf.createPersistentProperty("Max Angle", 180);
        minAngleProp = pf.createPersistentProperty("Min Angle", -180);
        turnPowerProp = pf.createPersistentProperty("Turn Speed", .03);
        currentAngleProp = pf.createEphemeralProperty("TurretAngle", 0);
        offsetProp = pf.createEphemeralProperty("Offset", 0);

        if (contract.isTurretReady()) {
            this.motor = factory.createCANTalon(contract.rotationMotor().channel);
            motor.setInverted(contract.rotationMotor().inverted);
        }

        motor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);

        scheduler.registerSubsystem(this);
    }

    public void turnLeft() {
        motor.simpleSet(turnPowerProp.get());
    }

    public void calibrateHere() {
        
    }

    public void turnRight() {
        motor.simpleSet(-turnPowerProp.get());
    }

    public void setPower(double power) {
        // if ((power < 0 && canTurnLeft()) || (power > 0 && canTurnRight())) {
        //     motor.simpleSet(power);
        // } else {
            motor.simpleSet(power);
        //}

    }

    public boolean canTurnRight() {
        return getCurrentAngle() <= maxAngleProp.get();
    }

    public boolean canTurnLeft() {
        return getCurrentAngle() >= minAngleProp.get();
    }

    public double getCurrentAngle() {
        return motor.getSelectedSensorPosition(0);
    }

    public void stop() {
        setPower(0);
    }

    @Override
    public void periodic() {
        currentAngleProp.set(getCurrentAngle());
    }
}
