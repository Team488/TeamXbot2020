package competition.subsystems.shooterwheel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;

import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class ShooterWheelSubsystem extends BaseSubsystem {

    final DoubleProperty spinWheelPowerProp;
    public XCANSparkMax neoMasterMotor;
    public XCANSparkMax neoFollowerMotor;
    IdealElectricalContract contract;

    @Inject
    public ShooterWheelSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating ShooterWheelSubsystem");
        pf.setPrefix(this);
        this.contract = contract;
        spinWheelPowerProp = pf.createPersistentProperty("Spinning Wheel Power", 1);

        if (contract.isShooterWheelReady()) {
            this.neoMasterMotor = factory.createCANSparkMax(contract.shooterMotorMaster().channel, this.getPrefix(),"ShooterMaster");
            this.neoFollowerMotor = factory.createCANSparkMax(contract.shooterMotorFollower().channel, this.getPrefix(),"ShooterFollower");
            neoFollowerMotor.follow(neoMasterMotor, true);
        }
    }

    public void spin() {
        setPower(spinWheelPowerProp.get());
    }

    public void setPower(double power) {
        if (contract.isShooterWheelReady()) {
            neoMasterMotor.set(power);
        }
    }

    public void stop() {
        setPower(0);
    }
}