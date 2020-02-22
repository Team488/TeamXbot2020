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

    final DoubleProperty liftPowerProp;
    final DoubleProperty reversePowerProp;
    private IdealElectricalContract contract;
    public XCANTalon motor;

    @Inject
    public KickerSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract){
        pf.setPrefix(this);
        this.contract = contract;
        liftPowerProp = pf.createPersistentProperty("LiftPower", 1);
        reversePowerProp = pf.createPersistentProperty("ReversePower", -0.25);

        if(contract.isKickerReady()){
          this.motor = factory.createCANTalon(contract.kickerMotor().channel);
          motor.setInverted(contract.kickerMotor().inverted);
        }

    }

    public void lift(){
        setPower(liftPowerProp.get());
    }

    public void reverse(){
        setPower(reversePowerProp.get());
    }

    public void stop(){
        setPower(0);
    }

    public void setPower(double power){
        if(contract.isKickerReady()){
            motor.simpleSet(power);
        }
    }
}