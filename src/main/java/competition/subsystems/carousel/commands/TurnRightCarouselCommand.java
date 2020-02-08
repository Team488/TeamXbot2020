package competition.subsystems.carousel.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.carousel.CarouselSubsystem;
import xbot.common.command.BaseCommand;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
public class TurnRightCarouselCommand extends BaseCommand{    
   
    final CarouselSubsystem carouselSubsystem;
    final OperatorInterface oi;
    final ShooterWheelSubsystem shooterWheel;

    @Inject
    public TurnRightCarouselCommand(OperatorInterface oi, CarouselSubsystem carouselSubsystem,
                                    ShooterWheelSubsystem shooterWheel){
        this.oi = oi;
        this.carouselSubsystem = carouselSubsystem;
        this.addRequirements(this.carouselSubsystem);
        this.shooterWheel = shooterWheel;
    }

    @Override
    public void initialize(){
        log.info("Initializing");
    }

    public void execute(){
        if(shooterWheel.isAtSpeed()){
        carouselSubsystem.turnRight();
        } else {
            carouselSubsystem.stop();
        }
    }
}