package competition.subsystems.turret;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
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
            this.motor = factory.createCANTalon(contract.turretMotor().channel);
            motor.setInverted(contract.turretMotor().inverted);
        }
    }

    public void turnLeft() {
        motor.simpleSet(turnPowerProp.get());
    }

    public void turnRight() {
        motor.simpleSet(-turnPowerProp.get());
    }

    public void setPower(double power) {
        // Check for any reason power should be constrained.
        if (aboveMaximumAngle()) {
            // Turned too far left. Only allow right/negative rotation.
            power = MathUtils.constrainDouble(power, -1, 0);
        }
        if (belowMinimumAngle()) {
            // Turned too far right. Only allow left/positive rotation.
            power = MathUtils.constrainDouble(power, 0, 1);
        }

        motor.simpleSet(power);
    }

    public boolean aboveMaximumAngle() {
        return getCurrentAngle() >= maxAngleProp.get();
    }

    public boolean belowMinimumAngle() {
        return getCurrentAngle() <= minAngleProp.get();
    }

    public double getCurrentAngle() {
        return motor.getSelectedSensorPosition(0);
    }

    public double getDefaultTurretPower() {
        return turnPowerProp.get();
    }

    public void stop() {
        setPower(0);
    }
}
