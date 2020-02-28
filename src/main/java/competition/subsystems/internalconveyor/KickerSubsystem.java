package competition.subsystems.internalconveyor;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;


@Singleton
public class KickerSubsystem extends BaseSubsystem { //makes conveyer transport balls to shooter

    private final DoubleProperty wheelLiftPowerProp;
    private final DoubleProperty wheelReversePowerProp;
    private final DoubleProperty rollerLiftPowerProp;
    private final DoubleProperty rollerReversePowerProp;

    private final DoubleProperty manualWheelLiftPowerProp;
    private final DoubleProperty manualWheelReversePowerProp;
    private final DoubleProperty manualRollerLiftPowerProp; 
    private final DoubleProperty manualRollerReversePowerProp; 

    private IdealElectricalContract contract;
    public XCANTalon wheelMotor;
    public XCANTalon rollerMotor;

    @Inject
    public KickerSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract){
        pf.setPrefix(this);
        this.contract = contract;
        wheelLiftPowerProp = pf.createPersistentProperty("Wheel Lift Power", 1);
        wheelReversePowerProp = pf.createPersistentProperty("Wheel Reverse Power", -0.25);
        rollerLiftPowerProp = pf.createPersistentProperty("Roller Lift Power", 1);
        rollerReversePowerProp = pf.createPersistentProperty("Roller Reverse Power", -0.25);

        manualWheelLiftPowerProp = pf.createPersistentProperty("Manual Wheel Lift Power", 1);
        manualWheelReversePowerProp = pf.createPersistentProperty("Manual Wheel Reverse Power", -0.15);
        manualRollerLiftPowerProp = pf.createPersistentProperty("Manual Roller Lift Power", 1);
        manualRollerReversePowerProp = pf.createPersistentProperty("Manual Roller Reverse Power", -0.15);

        if(contract.isKickerReady()){
            this.wheelMotor = factory.createCANTalon(contract.kickerMotor().channel);
            this.rollerMotor = factory.createCANTalon(contract.kickerRollerMotor().channel);
            this.wheelMotor.setInverted(contract.kickerMotor().inverted);
            this.rollerMotor.setInverted(contract.kickerRollerMotor().inverted);
        }
    }

    public void lift(){
        setWheelPower(wheelLiftPowerProp.get());
        setRollerPower(rollerLiftPowerProp.get());
    }

    public void manualLift(){
        setWheelPower(manualWheelLiftPowerProp.get());
        setRollerPower(manualRollerLiftPowerProp.get());
    }

    public void reverse(){
        setWheelPower(wheelReversePowerProp.get());
        setRollerPower(rollerReversePowerProp.get());
    }

    public void manualReverse(){
        setWheelPower(manualWheelReversePowerProp.get());
        setRollerPower(manualRollerReversePowerProp.get());
    }

    public void stop(){
        setWheelPower(0);
        setRollerPower(0);
    }

    public void setWheelPower(double power){
        if(contract.isKickerReady()){
            wheelMotor.simpleSet(power);
        }
    }

    public void setRollerPower(double power){
        if(contract.isKickerReady()){
            rollerMotor.simpleSet(power);
        }
    }
}