package competition.subsystems.carousel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.command.XScheduler;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.controls.sensors.XDigitalInput;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.BooleanProperty;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class CarouselSubsystem extends BaseSubsystem{

    final DoubleProperty leftCarouselPowerProp;
    final DoubleProperty rightCarouselPowerProp;
    private IdealElectricalContract contract;
    public XCANTalon carouselMotor;
    public XDigitalInput positionSensor;
    private final BooleanProperty positionSensorActivatedProp;
    private final DoubleProperty firingPowerProp;

    @Inject
    public CarouselSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract, XScheduler scheduler){
        pf.setPrefix(this);
        leftCarouselPowerProp = pf.createPersistentProperty("Left Carousel Power", 1);
        rightCarouselPowerProp = pf.createPersistentProperty("Right Carousel Power", -1);
        positionSensorActivatedProp = pf.createEphemeralProperty("Position Sensor Activated", false);
        firingPowerProp = pf.createProperty("Firing Power", 0.75);
        this.contract = contract;

        if(contract.isCarouselReady()){
            this.carouselMotor = factory.createCANTalon(contract.carouselMotor().channel);
            this.positionSensor = factory.createDigitalInput(contract.getSpindexerSensor().channel);
        }

        scheduler.registerSubsystem(this);
    }

    public boolean atIndexPosition() {
        if (contract.isCarouselReady()) {
            return positionSensor.get();
        }
        return false;
    }

    public double getFiringPower() {
        return firingPowerProp.get();
    }

    public void turnLeft(){
        setPower(leftCarouselPowerProp.get());
    }

    public void turnRight(){
        setPower(rightCarouselPowerProp.get());
    }

    public void stop(){
        setPower(0);
    }

    public void setPower(double power){
        if(contract.isCarouselReady()){
            carouselMotor.simpleSet(power);
        }
    }

    @Override
    public void periodic() {
        positionSensorActivatedProp.set(atIndexPosition());
    }
}