package competition.subsystems.hanger;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;
 
@Singleton
public class HangerSubsystem extends BaseSubsystem {

    double power;
    final DoubleProperty hangingPowerProp;
    final DoubleProperty extendHangerHeightProp;
    final DoubleProperty retractHangerHeightProp;

    @Inject
    public HangerSubsystem(CommonLibFactory factory, PropertyFactory pf) {
        log.info("Creating HangerSubsystem");
        pf.setPrefix(this);
        hangingPowerProp = pf.createPersistentProperty("Hanging Power", 1);
        extendHangerHeightProp = pf.createPersistentProperty("Extend Hanger Height", 1);    
        retractHangerHeightProp = pf.createPersistentProperty("Retract Hanger Height", 1);    
    }

    public void extendHanger () { //hanger extend up to objective
        setPower(extendHangerHeightProp.get());
    }

    public void grabber () { //grabs onto objective

    }

    public void retractHanger () { //retracts hanger to lift robot
        setPower(retractHangerHeightProp.get());
    }

    public void setPower (double power) {

    }

    public void stop () {
        setPower(0);
    }
}