package competition.subsystems.internalconveyor;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;


@Singleton
public class InternalConveyorSubsystem extends BaseSubsystem { //makes conveyer transport balls to shooter

    final DoubleProperty intakePowerProp;
    final DoubleProperty outtakePowerProp;

    @Inject
    public InternalConveyorSubsystem(CommonLibFactory factory, PropertyFactory pf){

        pf.setPrefix(this);
        intakePowerProp = pf.createPersistentProperty("IntakePower", 0.5);
        outtakePowerProp = pf.createPersistentProperty("OuttakePower", 0.5);
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
        
    }
}