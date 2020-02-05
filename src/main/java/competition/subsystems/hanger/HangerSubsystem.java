package competition.subsystems.hanger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
 
@Singleton
public class HangerSubsystem extends BaseSubsystem {

    double power;
    final DoubleProperty hangingPowerProp;
    final DoubleProperty extendHangerHeightProp;
    final DoubleProperty retractHangerHeightProp;
    public XCANTalon hangerMotor;
    final private IdealElectricalContract contract;

    @Inject
    public HangerSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating HangerSubsystem");
        pf.setPrefix(this);
        hangingPowerProp = pf.createPersistentProperty("Hanging Power", 1);
        extendHangerHeightProp = pf.createPersistentProperty("Extend Hanger Height", 1);    
        retractHangerHeightProp = pf.createPersistentProperty("Retract Hanger Height", 1);   
        this.contract = contract;

        if (contract.isHangingReady()) {
            this.hangerMotor = factory.createCANTalon(contract.hangerMotor().channel);
        }
    }

    public void extendHanger () {
        setPower(extendHangerHeightProp.get());
    }

    public void grabber () {

    }

    public void retractHanger () {
        setPower(retractHangerHeightProp.get());
    }

    public void setPower (double power) {
        if(contract.isHangingReady()) {
            hangerMotor.simpleSet(power);
        }
    }

    public void stop () {
        setPower(0);
    }
}