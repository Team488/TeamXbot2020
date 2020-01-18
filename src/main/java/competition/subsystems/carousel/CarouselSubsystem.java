package competition.subsystems.carousel;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

@Singleton
public class CarouselSubsystem extends BaseSubsystem{

    final DoubleProperty carouselPowerProp;

    @Inject
    public CarouselSubsystem(CommonLibFactory factory, PropertyFactory pf){
        pf.setPrefix(this);
        carouselPowerProp = pf.createPersistentProperty("Carousel Power", 1);
    }

    public void spin(){
        setPower(carouselPowerProp.get());
    }

    public void stop(){
        setPower(0);
    }

    public void setPower(double power){

    }
    

    

}