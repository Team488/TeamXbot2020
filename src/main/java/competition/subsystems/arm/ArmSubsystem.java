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
    final IdealElectricalContract contract;

    @Inject
    public ArmSubsystem(CommonLibFactory clf, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ArmSubsystem");
        pf.setPrefix(this);
        this.contract = contract;
        if (contract.isArmReady()) {
            this.armSolenoid = clf.createSolenoid(contract.getArmSolenoid().channel);
        } else {
            this.armSolenoid = null;
        }
    }

    public void up() {
        if (contract.isArmReady()) {
            armSolenoid.setOn(true);
        }
    }

    public void down() {
        if (contract.isArmReady()) {
            armSolenoid.setOn(false);
        }
    }

}