package competition.subsystems.turret;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import competition.subsystems.pose.PoseSubsystem;
import xbot.common.command.BaseSetpointSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class TurretSubsystem extends BaseSetpointSubsystem {

    private final IdealElectricalContract contract;
    public XCANTalon motor;
    private double calibrationOffset;
    private final DoubleProperty maxAngleProp;
    private final DoubleProperty minAngleProp;
    private final DoubleProperty turnPowerProp;
    private final DoubleProperty defaultForwardHeadingProp;
    private final DoubleProperty ticksPerDegreeProp;
    private final BooleanProperty calibratedProp;
    private final DoubleProperty currentAngleProp;
    private double goalAngle;
    private final BooleanProperty rightLimitProp;
    private final BooleanProperty leftLimitProp;

    final PoseSubsystem pose;


    @Inject
    public TurretSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract,
    XScheduler scheduler, PoseSubsystem pose) {
        log.info("Creating TurretSubsystem");
        pf.setPrefix(this);
        this.contract = contract;
        this.pose = pose;

        calibrationOffset = 0;
        maxAngleProp = pf.createPersistentProperty("Max Angle", 180);
        minAngleProp = pf.createPersistentProperty("Min Angle", -180);
        turnPowerProp = pf.createPersistentProperty("Turn Speed", .03);
        defaultForwardHeadingProp = pf.createPersistentProperty("Default Forward Heading", 90);
        ticksPerDegreeProp = pf.createPersistentProperty("Ticks Per Degree", 1);
        calibratedProp = pf.createEphemeralProperty("Calibrated", false);
        currentAngleProp = pf.createEphemeralProperty("Current Angle", 0);
        rightLimitProp = pf.createEphemeralProperty("Over Right Limit", false);
        leftLimitProp = pf.createEphemeralProperty("Over Left Limit", false);

        if (contract.isTurretReady()) {
            this.motor = factory.createCANTalon(contract.turretMotor().channel);
            motor.configureAsMasterMotor(
                this.getPrefix(), 
                "TurretMotor", 
                contract.turretMotor().inverted, 
                contract.turretEncoder().inverted);
        }

        scheduler.registerSubsystem(this);
    }

    public void calibrateTurret(){ //here
        calibrationOffset = getCurrentRawAngle();
        log.info("Angle set to the default of" + defaultForwardHeadingProp.get());
        setIsCalibrated(true);
    }

    public void uncalibrate() {
        setIsCalibrated(false);
    }

    private void setIsCalibrated(boolean value) {
        calibratedProp.set(value);
    }

    public boolean isCalibrated() {
        return calibratedProp.get();
    }

    public void turnLeft() {
        motor.simpleSet(turnPowerProp.get());
    }

    public void turnRight() {
        motor.simpleSet(-turnPowerProp.get());
    }
    
    public void setPower(double power) {
        if (isCalibrated()) {
            // No sense running the protection code if we don't know where we are.
            
            // Check for any reason power should be constrained.
            if (aboveMaximumAngle() || rightLimitStop()) {
                // Turned too far left. Only allow right/negative rotation.
                power = MathUtils.constrainDouble(power, -1, 0);
            }
            if (belowMinimumAngle() || LeftLimitStop()) {
                // Turned too far right. Only allow left/positive rotation.
                power = MathUtils.constrainDouble(power, 0, 1);
            }
        }

        if (contract.isTurretReady()) {
            motor.simpleSet(power);
        }
    }

    public boolean aboveMaximumAngle() {
        return getCurrentAngle() >= maxAngleProp.get();
    }

    public boolean belowMinimumAngle() {
        return getCurrentAngle() <= minAngleProp.get();
    }

    public boolean LeftLimitStop(){
        return motor.isRevLimitSwitchClosed();
    }

    public boolean rightLimitStop(){
        return motor.isFwdLimitSwitchClosed();
    }

    public double getCurrentAngle() {
        double ticks = getCurrentRawAngle() - calibrationOffset;
        return (ticks / ticksPerDegreeProp.get()) + defaultForwardHeadingProp.get();
    }

    private double getCurrentRawAngle() {
        if (contract.isTurretReady()) {
            return motor.getSelectedSensorPosition(0); 
        }
        return 0;
    }

    public double getDefaultTurretPower() {
        return turnPowerProp.get();
    }

    public void stop() {
        setPower(0);
    }

    public double getTicksPerDegree() {
        return ticksPerDegreeProp.get();
    }

    public double getMaxAngle() {
        return maxAngleProp.get();
    }

    public double getMinAngle() {
        return minAngleProp.get();
    }

    @Override
    public void periodic() {
        currentAngleProp.set(getCurrentAngle());
        leftLimitProp.set(LeftLimitStop());
        rightLimitProp.set(rightLimitStop());
    }

    public void setGoalAngle(double angle) {
        goalAngle = angle;
    }

    public double getGoalAngle() {
        return goalAngle;
    }

    public void setFieldOrientedGoalAngle(double angle) {
        // Take the current robot heading, recast it into turret units
        double robotHeading = pose.getCurrentHeading().shiftBounds(90).getValue();
        // Formula is: Robot Oriented Turret Heading = Desired FO Turret Heading - Robot FO Heading + 90
        double turretGoalHeading = angle - robotHeading + 90;
        setGoalAngle(turretGoalHeading);
    }

    @Override
    public double getCurrentValue() {
        return getCurrentAngle();
    }

    @Override
    public double getTargetValue() {
        return getGoalAngle();
    }

    @Override
    public void setTargetValue(double value) {
        setGoalAngle(value);
    }
}
