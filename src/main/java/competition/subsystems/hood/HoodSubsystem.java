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

    final DoubleProperty calibrationOffsetProp;
    final DoubleProperty extendPowerProp;
    final DoubleProperty retractPowerProp;
    final DoubleProperty rangeProp;
    final BooleanProperty calibratedProp;
    final DoubleProperty currentPercentExtendedProp;
    private IdealElectricalContract contract;
    public XCANTalon hoodMotor;
    final DoubleProperty goalAngleProp;

    double rawPosition; //for testing

    @Inject
    public HoodSubsystem (CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract,
    XScheduler scheduler){
        log.info("Creating HoodSubsystem");
        pf.setPrefix(this);
        this.contract = contract;

        calibrationOffsetProp = pf.createPersistentProperty("Calibration Offset", 0);
        extendPowerProp = pf.createPersistentProperty("Extend Power", 1);
        retractPowerProp = pf.createPersistentProperty("Retract Power", -1);
        rangeProp = pf.createPersistentProperty("Range", 1000);
        currentPercentExtendedProp = pf.createEphemeralProperty("Current Percent Extended", 0);
        calibratedProp = pf.createEphemeralProperty("Calibrated", false);
        goalAngleProp = pf.createEphemeralProperty("Goal Angle", 0);

        if (contract.isHoodReady()) {
             this.hoodMotor = factory.createCANTalon(contract.hoodMotor().channel);
             hoodMotor.configureAsMasterMotor(this.getPrefix(), "Hood", contract.hoodMotor().inverted, false);
        }

        scheduler.registerSubsystem(this);
    }

    public void setAngle(double angle){
        currentPercentExtendedProp.set(angle);
    }

    public void calibrateHood(){
        calibrationOffsetProp.set(getCurrentRawPosition());
        setIsCalibrated(true);
    }
    
    public void setIsCalibrated(boolean value){
        calibratedProp.set(value);
    }

    public boolean isCalibrated(){
        return calibratedProp.get();
    }

    public void setGoalAngle(double angle){
        goalAngleProp.set(angle);
    }

    public double getGoalAngle(){
        return goalAngleProp.get();
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
        return (currentPercentExtendedProp.get() >= 1);
    }

    public boolean isFullyRetracted(){
        return (currentPercentExtendedProp.get() <= 0);
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
        currentPercentExtendedProp.set(getPercentExtended());
    }

    public double getPercentExtended(){
        double ticks = (getCurrentRawPosition() - calibrationOffsetProp.get());
        return (ticks / rangeProp.get());
    }

    public double getCurrentRawPosition(){
        if(contract.isHoodReady()){
            return hoodMotor.getSelectedSensorPosition(0);
        }
        return 0;
    }

    @Override
    public double getCurrentValue(){
        return getPercentExtended();
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