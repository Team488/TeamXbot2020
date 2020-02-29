package competition.commandgroups;

import com.google.inject.Inject;
import competition.subsystems.carousel.commands.TurnLeftCarouselCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;

public class UnjamCarouselCommand extends SequentialCommandGroup{

    @Inject

    public UnjamCarouselCommand(TurnLeftCarouselCommand turnLeft, TurnLeftCarouselCommand turnRight) {

        var timedForShaking = new DelayViaSupplierCommand(() -> 0.75);
        
        var turnLeftTimed = new ParallelDeadlineGroup(timedForShaking, turnLeft);
        var turnRightTimed = new ParallelDeadlineGroup(timedForShaking, turnRight);

        this.addCommands(turnLeftTimed, turnRightTimed);

    }

}