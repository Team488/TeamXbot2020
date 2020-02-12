package competition.subsystems.turret;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
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


    @Inject
    public TurretSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating TurretSubsystem");
        pf.setPrefix(this);
        currentAngle = 0;
        maxAngleProp = pf.createPersistentProperty("Max Angle", 180);
        minAngleProp = pf.createPersistentProperty("Min Angle", -180);
        turnPowerProp = pf.createPersistentProperty("Turn Speed", .03);

        if (contract.isTurretReady()) {
            this.motor = factory.createCANTalon(contract.rotationMotor().channel);
            motor.setInverted(contract.rotationMotor().inverted);
        }
    }

    public void turnLeft() {
        motor.simpleSet(turnPowerProp.get());
    }

    public void turnRight() {
        motor.simpleSet(-turnPowerProp.get());
    }

    public void setPower(double power) {
        if (canTurnLeft(power)) {
            motor.simpleSet(power);
        } else if (canTurnRight(power)) {
            motor.simpleSet(power);
        } else if (power == 0) {
            motor.simpleSet(power);
        }
    }

    public boolean canTurnRight(double power) {
        return (getCurrentAngle() >= maxAngleProp.get()) && (power < 0);
    }

    public boolean canTurnLeft(double power) {
        return (getCurrentAngle() <= minAngleProp.get()) && (power > 0);
    }

    public double getCurrentAngle() {
        return currentAngle;
    }

    public void stop() {
        setPower(0);
    }
}
