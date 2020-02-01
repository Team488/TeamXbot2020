package competition.subsystems.internalconveyor;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.ElectricalContract;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;


@Singleton
public class InternalConveyorSubsystem extends BaseSubsystem { //makes conveyer transport balls to shooter

    final DoubleProperty intakePowerProp;
    final DoubleProperty outtakePowerProp;
    private ElectricalContract contract;
    public XCANTalon intakeMotor;

    @Inject
    public InternalConveyorSubsystem(CommonLibFactory factory, PropertyFactory pf, ElectricalContract contract){

        pf.setPrefix(this);
        this.contract = contract;
        intakePowerProp = pf.createPersistentProperty("IntakePower", 0.5);

        outtakePowerProp = pf.createPersistentProperty("OuttakePower", -0.5);



        if(contract.isConveyorReady()){
          this.intakeMotor = factory.createCANTalon(contract.intakeMotor().channel);
          intakeMotor.setInverted(contract.intakeMotor().inverted);
        }

    }

    public void intake(){
        setPower(intakePowerProp.get());
    }

    public void outtake(){
        setPower(outtakePowerProp.get());
    }

    public void stop(){
        setPower(0);
    }

    public void setPower(double power){
        if(contract.isConveyorReady()){
            intakeMotor.simpleSet(power);
        }
    }
}