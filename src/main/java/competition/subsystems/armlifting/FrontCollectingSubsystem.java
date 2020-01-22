package competition.subsystems.armlifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;


@Singleton
public class FrontCollectingSubsystem extends BaseSubsystem { // maybe call this FrontCollectionSubsystem
    
    final DoubleProperty intakePowerProp;
    double power;
    int currentTotalBalls = 0;
    public XCANTalon frontCollectingMotor;
    private IdealElectricalContract contract;


    @Inject
    public FrontCollectingSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating CollectingSubsystem");
        pf.setPrefix(this);
        intakePowerProp = pf.createPersistentProperty("Intake Power", 1);

        if (contract.isFrontCollectingReady()) {
            this.frontCollectingMotor = factory.createCANTalon(contract.frontCollectingMotor().channel);
        }
    }

    public void intake() {
        setPower(intakePowerProp.get());
    }

    public void setPower(double power){
        if(contract.isFrontCollectingReady()) {
            frontCollectingMotor.simpleSet(power);
        }
    }
    
    public boolean isAtCapacity() {
        if (currentTotalBalls >= 5) {
            return false;
        } else {
            return true;
        }
    }

    public void stop () {
        setPower(0);

    }
}