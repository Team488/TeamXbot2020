package competition.commandgroups;

import com.google.inject.Inject;
import competition.subsystems.carousel.commands.TurnLeftCarouselCommand;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class ShakeCarouselCommand extends SequentialCommandGroup {

    private final DoubleProperty shakeDurationProp;

    @Inject
    public ShakeCarouselCommand(TurnLeftCarouselCommand turnLeft, TurnLeftCarouselCommand turnRight, PropertyFactory pf) {
        pf.setPrefix(this.getName());
        shakeDurationProp = pf.createPersistentProperty("Shake Duration", 0.75);

        DelayViaSupplierCommand leftShakeWait = new DelayViaSupplierCommand(() -> shakeDurationProp.get());
        DelayViaSupplierCommand rightShakeWait = new DelayViaSupplierCommand(() -> shakeDurationProp.get());
        var turnLeftTimed = new ParallelDeadlineGroup(leftShakeWait, turnLeft);
        var turnRightTimed = new ParallelDeadlineGroup(rightShakeWait, turnRight);

        this.addCommands(turnLeftTimed, turnRightTimed);
    }

}