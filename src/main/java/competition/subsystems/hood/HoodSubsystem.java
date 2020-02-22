package competition.subsystems.hood;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import competition.IdealElectricalContract;
import xbot.common.command.BaseSetpointSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.math.MathUtils;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class HoodSubsystem extends BaseSetpointSubsystem{

    private DoubleProperty calibrationOffset;
    final DoubleProperty extendPowerProp;
    final DoubleProperty retractPowerProp;
    final DoubleProperty maxAngleProp;
    final DoubleProperty minAngleProp;
    final DoubleProperty ticksPerDegreeProp;
    final BooleanProperty calibratedProp;
    final DoubleProperty currentAngleProp;
    private IdealElectricalContract contract;
    public XCANTalon hoodMotor;
    double goalAngle;

    @Inject
    public HoodSubsystem (CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract,
    XScheduler scheduler){
        log.info("Creating HoodSubsystem");
        pf.setPrefix(this);
        this.contract = contract;

        calibrationOffset = pf.createPersistentProperty("Calibration Offset", 0);
        extendPowerProp = pf.createPersistentProperty("Extend Power", 1);
        retractPowerProp = pf.createPersistentProperty("Retract Power", -1);
        maxAngleProp = pf.createPersistentProperty("Max Angle", 5);
        minAngleProp = pf.createPersistentProperty("Min Angle", -5);
        ticksPerDegreeProp = pf.createPersistentProperty("Ticks Per Degree", 1);
        currentAngleProp = pf.createEphemeralProperty("Current Angle", 0);
        calibratedProp = pf.createEphemeralProperty("Calibrated", false);

        if (contract.isHoodReady()) {
             this.hoodMotor = factory.createCANTalon(contract.hoodMotor().channel);
        }

        scheduler.registerSubsystem(this);
    }

    public void setAngle(double angle){
        currentAngleProp.set(angle);
    }

    public void calibrateHood(){
        calibrationOffset.set(getCurrentRawAngle());
        setIsCalibrated(true);
    }
    
    public void setIsCalibrated(boolean value){
        calibratedProp.set(value);
    }

    public boolean isCalibrated(){
        return calibratedProp.get();
    }

    public void setGoalAngle(double angle){
        goalAngle = angle;
    }

    public double getGoalAngle(){
        return goalAngle;
    }

    public void extend(){
        setPower(extendPowerProp.get());
    }

    public void retract(){
        setPower(retractPowerProp.get());
    }

    public void stop(){
        setPower(0);
    }

    public boolean isFullyExtended(){
        return (currentAngleProp.get() >= maxAngleProp.get());
    }

    public boolean isFullyRetracted(){
        return (currentAngleProp.get() <= minAngleProp.get());
    }

    public void setPower(double power){
        if(isCalibrated()){

            if(isFullyExtended()){
            power = MathUtils.constrainDouble(power, -1, 0);
            }
            if(isFullyRetracted()){
            power = MathUtils.constrainDouble(power, 0, 1);
            }
        }

        if (contract.isHoodReady()) {
            hoodMotor.simpleSet(power);
        }
    }

    public void periodic(){
        currentAngleProp.set(getCurrentAngle());
    }

    public double getCurrentAngle(){
        double ticks = getCurrentRawAngle() - calibrationOffset.get();
        return (ticks / ticksPerDegreeProp.get());
    }

    public double getCurrentRawAngle(){
        if(contract.isHoodReady()){
            return hoodMotor.getSelectedSensorPosition(0);
        }
        return 0;
    }

    @Override
    public double getCurrentValue(){
        return getCurrentAngle();
    }

    @Override
    public double getTargetValue(){
        return getGoalAngle();
    }

    @Override
    public void setTargetValue(double value){
        setGoalAngle(value);
    }
}