package competition.subsystems.arm;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XSolenoid;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.PropertyFactory;
 
@Singleton
public class ArmSubsystem extends BaseSubsystem {

    public final XSolenoid armSolenoid;

    @Inject
    public ArmSubsystem(CommonLibFactory clf, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ArmSubsystem");
        pf.setPrefix(this);
        if (contract.isArmReady()) {
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