package competition.subsystems.armlifting;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.PropertyFactory;
 
@Singleton
public class CollectorArmLiftingSubsystem extends BaseSubsystem {

    public final XSolenoid armSolenoid;

    @Inject
    public CollectorArmLiftingSubsystem(CommonLibFactory clf, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating LiftingSubsystem");
        pf.setPrefix(this);
        if (contract.isCollectorArmLiftingReady()) {
            this.armSolenoid = clf.createSolenoid(contract.getArmSolenoid().channel);
        } else {
            this.armSolenoid = null;
        }
    }

    public void up() {
        armSolenoid.setOn(true);
    }

    public void down() {
        armSolenoid.setOn(false);
    }

}