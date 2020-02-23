package competition.subsystems.intake;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANSparkMax;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class IntakeSubsystem extends BaseSubsystem {

    final DoubleProperty intakePowerProp;
    int currentTotalBalls = 0;
    public XCANSparkMax intakeMotor;
    final IdealElectricalContract contract;

    @Inject
    public IntakeSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract) {
        log.info("Creating IntakeSubsystem");
        pf.setPrefix(this);
        intakePowerProp = pf.createPersistentProperty("Intake Power", 1);
        this.contract = contract;

        if (contract.isIntakeReady()) {
            this.intakeMotor = factory.createCANSparkMax(contract.intakeMotor().channel, this.getPrefix(), "Collector");
            intakeMotor.setInverted(contract.intakeMotor().inverted);
        }
    }

    public void intake() {
        setPower(intakePowerProp.get());
    }

    public void eject() {
        setPower(-intakePowerProp.get());
    }

    public boolean isAtCapacity() {
        if (currentTotalBalls >= 5) {
            return true;
        } else {
            return false;
        }
    }

    public void setCurrentTotalBalls(int currentTotalBalls) {
        this.currentTotalBalls = currentTotalBalls;
    }

    public void setPower(double power) {

        if (isAtCapacity()) {
            power = 0;
        }

        if (contract.isIntakeReady()) {
            intakeMotor.set(power);
        }
    }

    public void stop() {
        setPower(0);

    }
}