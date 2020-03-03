package competition.autonomous;

import com.google.inject.Inject;

import org.apache.log4j.Logger;

import competition.subsystems.drive.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.ParallelRaceGroup;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;
import xbot.common.properties.PropertyFactory;

// Right now having this class extend SequentialCommandGroup is overkill
// since it only does one simple thing, but experience has suggested that
// autonomous commands only get more complicated with time.
public class OnlyDriveAutonomousCommand extends SequentialCommandGroup {

    private static Logger log = Logger.getLogger(OnlyDriveAutonomousCommand.class);
    
    @Inject
    public OnlyDriveAutonomousCommand(DriveSubsystem drive, PropertyFactory pf, CommonAutonomousSubsystem commonAuto) {
        double power = commonAuto.getSimpleDrivePower();
        RunCommand move = new RunCommand(() -> drive.tankDrive(power, power), drive);
        DelayViaSupplierCommand wait = new DelayViaSupplierCommand(() -> commonAuto.getSimpleDriveTime());

        var sequence = new ParallelRaceGroup(wait, move);

        this.addCommands(sequence);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
    }
}