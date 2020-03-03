package competition.autonomous;

import com.google.inject.Inject;

import org.apache.log4j.Logger;

import competition.commandgroups.ShootCommand;
import competition.commandgroups.ShutdownShootingCommand;
import competition.multisubsystemcommands.SetWheelAndHoodGoalsCommand;
import competition.multisubsystemcommands.SetWheelAndHoodGoalsCommand.FieldPosition;
import competition.subsystems.drive.commands.AutonomousDriveCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class BasicAutonomousCommand extends SequentialCommandGroup {

    private final DoubleProperty delayBeforeShootProp;
    private static Logger log = Logger.getLogger(BasicAutonomousCommand.class);

    @Inject
    public BasicAutonomousCommand(
        PropertyFactory pf,
        SetWheelAndHoodGoalsCommand setGoals,
        ShootCommand shoot,
        ShutdownShootingCommand stopShooting,
        AutonomousDriveCommand drive)
    {
        pf.setPrefix(BasicAutonomousCommand.class.getName());
        delayBeforeShootProp = pf.createPersistentProperty("Delay before auto fire seconds", 0.0);

        var delayBeforeShoot = new DelayViaSupplierCommand(() -> delayBeforeShootProp.get());
        shoot.setStreamFire();
        setGoals.setGoals(FieldPosition.InitiationCloseToGoal);
        // ParallelDeadline: TimeToShoot, combined with SetGoals/Prepare/SpinCarousel
        // ParallelDeadine: TimeToDrive, combined with StopShooting/DriveBackwards
        var timeToShoot = new DelayViaSupplierCommand(() -> 10.0);
        var timeToDrive = new DelayViaSupplierCommand(() -> 1.0);
        drive.setDrivePower(0.5, 0.5);

        var shootSequence = new ParallelCommandGroup(setGoals, shoot);
        var driveSequence = new ParallelCommandGroup(stopShooting, drive);
        
        var shootForTime = new ParallelDeadlineGroup(timeToShoot, shootSequence);
        var driveFortime = new ParallelDeadlineGroup(timeToDrive, driveSequence);

        this.addCommands(delayBeforeShoot, shootForTime, driveFortime);
    }

    @Override
    public void initialize() {
        super.initialize();
        log.info("Initializing");
    }
}