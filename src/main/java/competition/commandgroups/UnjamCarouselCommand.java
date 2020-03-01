package competition.commandgroups;

import com.google.inject.Inject;
import competition.subsystems.carousel.commands.TurnLeftCarouselCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;

public class UnjamCarouselCommand extends SequentialCommandGroup {

    @Inject
    public UnjamCarouselCommand(TurnLeftCarouselCommand turnLeft, TurnLeftCarouselCommand turnRight) {
        DelayViaSupplierCommand leftShakeWait = new DelayViaSupplierCommand(() -> Math.random() * 0.5 + 0.25);
        DelayViaSupplierCommand rightShakeWait = new DelayViaSupplierCommand(() -> Math.random() * 0.5 + 0.25);
        var turnLeftTimed = new ParallelDeadlineGroup(leftShakeWait, turnLeft);
        var turnRightTimed = new ParallelDeadlineGroup(rightShakeWait, turnRight);

        this.addCommands(turnLeftTimed, turnRightTimed);
    }

}