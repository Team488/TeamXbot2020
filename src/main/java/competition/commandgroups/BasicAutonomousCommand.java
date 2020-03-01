package competition.commandgroups;

import com.google.inject.Inject;

import competition.multisubsystemcommands.SetWheelAndHoodGoalsCommand;
import competition.subsystems.carousel.commands.CarouselFiringModeCommand;
import competition.subsystems.drive.commands.AutonomousDriveCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class BasicAutonomousCommand extends SequentialCommandGroup {

    private final DoubleProperty delayBeforeShootProp;

    @Inject
    public BasicAutonomousCommand(
        PropertyFactory pf,
        SetWheelAndHoodGoalsCommand setGoals,
        PrepareToFireCommand prepare,
        CarouselFiringModeCommand spinCarousel,
        ShutdownShootingCommand stopShooting,
        AutonomousDriveCommand drive)
    {
        pf.setPrefix(BasicAutonomousCommand.class.getName());
        delayBeforeShootProp = pf.createPersistentProperty("Delay before auto fire", 0.0);

        var delayBeforeShoot = new DelayViaSupplierCommand(() -> delayBeforeShootProp.get());

        // ParallelDeadline: TimeToShoot, combined with SetGoals/Prepare/SpinCarousel
        // ParallelDeadine: TimeToDrive, combined with StopShooting/DriveBackwards
        var timeToShoot = new DelayViaSupplierCommand(() -> 10.0);
        var timeToDrive = new DelayViaSupplierCommand(() -> 1.0);
        drive.setDrivePower(0.5, 0.5);

        var shootSequence = new ParallelCommandGroup(setGoals, prepare, spinCarousel);
        var driveSequence = new ParallelCommandGroup(stopShooting, drive);
        
        var shootForTime = new ParallelDeadlineGroup(timeToShoot, shootSequence);
        var driveFortime = new ParallelDeadlineGroup(timeToDrive, driveSequence);

        this.addCommands(delayBeforeShoot, shootForTime, driveFortime);
    }
}