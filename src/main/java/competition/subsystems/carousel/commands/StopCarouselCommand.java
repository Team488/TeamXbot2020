package competition.subsystems.carousel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.carousel.CarouselSubsystem;
import xbot.common.command.BaseCommand;

public class StopCarouselCommand extends BaseCommand{
     
    final CarouselSubsystem carouselSubsystem;
    final OperatorInterface oi;

    @Inject
    public StopCarouselCommand(OperatorInterface oi, CarouselSubsystem carouselSubsystem){
        this.oi = oi;
        this.carouselSubsystem = carouselSubsystem;
        this.addRequirements(this.carouselSubsystem);
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
       carouselSubsystem.stop();
    }
}