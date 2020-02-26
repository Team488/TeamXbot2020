package competition.subsystems.climber;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.revrobotics.ControlType;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class ClimberSubsystem extends BaseSubsystem {

    double power;
    final DoubleProperty minPower;

    public enum ClimberSide {
        Left, Right
    }

    final DoubleProperty climberPowerProp;
    final DoubleProperty maxClimberTicksProp;
    public XCANSparkMax leftMotor;
    public XCANSparkMax rightMotor;
    final IdealElectricalContract contract;
    public final XSolenoid climbSolenoid;
    final DoubleProperty slowZoneProp;
    final DoubleProperty slowZoneFactorProp;
    final DoubleProperty defaultPowerProp;
    final DoubleProperty unsafeExtensionProp;
    final DoubleProperty catchUpFactorProp;

    @Inject
    public ClimberSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ClimberSubsystem");
        pf.setPrefix(this);

        climberPowerProp = pf.createPersistentProperty("Climber Power", 1);
        minPower = pf.createPersistentProperty("Minimium Power", 0.1);
        maxClimberTicksProp = pf.createPersistentProperty("Maximum Extension", 100);
        slowZoneProp = pf.createPersistentProperty("Slow Zone Size", 10);
        slowZoneFactorProp = pf.createPersistentProperty("Slow Zone Factor", 0.35);
        defaultPowerProp = pf.createPersistentProperty("Default Power", 0.5);
        unsafeExtensionProp = pf.createPersistentProperty("Unsafe Extension Distance", 3);
        catchUpFactorProp = pf.createPersistentProperty("Catch Up Factor", 0.333);

        this.climbSolenoid = factory.createSolenoid(contract.getClimbSolenoid().channel);
        this.contract = contract;

        if (contract.isClimberReady()) {
            this.leftMotor = factory.createCANSparkMax(contract.leftClimberMotor().channel, this.getPrefix(),
                    "LeftMotor");
            this.rightMotor = factory.createCANSparkMax(contract.rightClimberMotor().channel, this.getPrefix(),
                    "RightMotor");
        }
    }

    private XCANSparkMax getMotorForSide(ClimberSide side) {
        if (side == ClimberSide.Left) {
            return leftMotor;
        }
        return rightMotor;
    }

    public double getPosition(ClimberSide side) {
        if (contract.isClimberReady()) {
            return getMotorForSide(side).getPosition();
        }
        return 0;
    }

    private void setPositionalGoal(double positionGoal, ClimberSide side) {
        if (contract.isClimberReady()) {
            getMotorForSide(side).setReference(positionGoal, ControlType.kPosition);
        }
    }

    private void setRawPower(double power, ClimberSide side) {
        if (contract.isClimberReady()) {
            getMotorForSide(side).set(power);
        }
    }

    private double applyDeadbandAndBrake(double power) {
        power = MathUtils.deadband(power, minPower.get());
        if (Math.abs(power) > 0.001) {
            climbSolenoid.setOn(true);
        } else {
            climbSolenoid.setOn(false);
        }
        return power;
    }

    /**
     * Designed to keep the two sides in sync when extending or retracting. If the
     * sides get too far apart, it applies an extra speed differential in order to
     * let them synchronize again.
     * 
     * @param power Climb power to use.
     */
    public void dynamicClimb(double power) {
        power = applyDeadbandAndBrake(power);

        double leftPower = power;
        double rightPower = power;

        double delta = getPosition(ClimberSide.Left) - getPosition(ClimberSide.Right);
        double catchUp = delta * catchUpFactorProp.get();

        leftPower -= catchUp;
        rightPower += catchUp;

        setPower(leftPower, ClimberSide.Left);
        setPower(rightPower, ClimberSide.Right);
    }

    public void setPower(double power) {
        power = applyDeadbandAndBrake(power);
        setPower(power, ClimberSide.Left);
        setPower(power, ClimberSide.Right);
    }

    private void setPower(double power, ClimberSide side) {

        if (getPosition(side) < 0) {
            power = MathUtils.constrainDouble(power, 0, 1);
        }

        if (getPosition(side) < slowZoneProp.get()) {
            power *= slowZoneFactorProp.get();
        }

        if (getPosition(side) > maxClimberTicksProp.get()) {
            power = MathUtils.constrainDouble(power, -1, 0);
        }

        setRawPower(power, side);
    }

    public void extend() {
        setPower(defaultPowerProp.get());
    }

    public void retract() {
        setPower(-defaultPowerProp.get());
    }

    public void stop() {
        setPower(0);
    }

    public boolean unsafeToLowerArm() {
        return getPosition(ClimberSide.Left) > unsafeExtensionProp.get()
                || getPosition(ClimberSide.Right) > unsafeExtensionProp.get();
    }

    public double getSlowZoneRange() {
        return slowZoneProp.get();
    }

    public double getSlowZoneFactor() {
        return slowZoneFactorProp.get();
    }

    public double getDefaultPower() {
        return defaultPowerProp.get();
    }

    public double getMaximumExtension() {
        return maxClimberTicksProp.get();
    }

    public double getCatchUpFactor() {
        return catchUpFactorProp.get();
    }
}