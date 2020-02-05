package competition.subsystems.carousel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.IdealElectricalContract;
import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XCANTalon;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class CarouselSubsystem extends BaseSubsystem{

    final DoubleProperty leftCarouselPowerProp;
    final DoubleProperty rightCarouselPowerProp;
    private IdealElectricalContract contract;
    public XCANTalon carouselMotor;


    @Inject
    public CarouselSubsystem(CommonLibFactory factory, PropertyFactory pf, IdealElectricalContract contract){
        pf.setPrefix(this);
        leftCarouselPowerProp = pf.createPersistentProperty("Left Carousel Power", 1);
        rightCarouselPowerProp = pf.createPersistentProperty("Right Carousel Power", -1);
        this.contract = contract;

        if(contract.isCarouselReady()){
            this.carouselMotor = factory.createCANTalon(contract.carouselMotor().channel);
        }
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
    

    

}