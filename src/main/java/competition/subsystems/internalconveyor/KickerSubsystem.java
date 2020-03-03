package competition.subsystems.internalconveyor;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.logic.TimeStableValidator;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;


@Singleton
public class KickerSubsystem extends BaseSubsystem { //makes conveyer transport balls to shooter

    private final DoubleProperty wheelLiftPowerProp;
    private final DoubleProperty wheelReversePowerProp;
    private final BooleanProperty isKickerClearProp;

    private IdealElectricalContract contract;
    public XCANTalon wheelMotor;

    private final DoubleProperty cellClearTimerProp;
    private final TimeStableValidator cellClearTimer;

    @Inject
    public KickerSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract, XScheduler scheduler){
        pf.setPrefix(this);
        this.contract = contract;
        wheelLiftPowerProp = pf.createPersistentProperty("Wheel Lift Power", 1);
        wheelReversePowerProp = pf.createPersistentProperty("Wheel Reverse Power", -0.25);
        cellClearTimerProp = pf.createPersistentProperty("Cell Clear Timer", 0.75);
        cellClearTimer = new TimeStableValidator(() -> cellClearTimerProp.get());
        isKickerClearProp = pf.createEphemeralProperty("Is Kicker Clear", false);

        if(contract.isKickerReady()){
            this.wheelMotor = factory.createCANTalon(contract.kickerMotor().channel);
            this.wheelMotor.setInverted(contract.kickerMotor().inverted);
        }

        scheduler.registerSubsystem(this);
    }

    public void lift(){
        setWheelPower(wheelLiftPowerProp.get());
    }

    public void reverse(){
        setWheelPower(wheelReversePowerProp.get());
    }

    public void stop(){
        setWheelPower(0);
    }

    public void setPower(double power) {
        setWheelPower(power);
    }

    private void setWheelPower(double power){
        if(contract.isKickerReady()){
            wheelMotor.simpleSet(power);
        }
    }

    private double getPower() {
        if (contract.isKickerReady()) {
            return wheelMotor.getMotorOutputPercent();
        }
        return 0;
    }

    public boolean isKickerLikelyClear() {
        return feedCellClearTimer();
    }

    private boolean feedCellClearTimer() {
        return cellClearTimer.checkStable(getPower() > 0.5);
    }

    @Override
    public void periodic() {
        isKickerClearProp.set(feedCellClearTimer());
    }
}