package competition.commandgroups;

import com.google.inject.Inject;

import org.apache.log4j.Logger;

import competition.subsystems.carousel.CarouselSubsystem;
import competition.subsystems.internalconveyor.KickerSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import xbot.common.command.DelayViaSupplierCommand;

public class ShutdownShootingCommand extends SequentialCommandGroup {

    private static Logger log = Logger.getLogger(ShutdownShootingCommand.class);

    @Inject
    public ShutdownShootingCommand(CarouselSubsystem carousel, KickerSubsystem kicker, ShooterWheelSubsystem wheel) {
        DelayViaSupplierCommand waitCommand = new DelayViaSupplierCommand(() -> 1.0);

        // Stop the carousel immediately, while the kicker and shooter wheel are still running.
        // Wait a little bit for any cells in the main firing path to clear
        // Turn of the kicker, return the shooter wheel goal to 0.

        this.addCommands(
            new InstantCommand(carousel::stop, carousel),
            waitCommand,
            new InstantCommand(kicker::stop, kicker),
            new InstantCommand(
                () -> wheel.setTargetRPM(0), wheel.getSetpointLock()));
    }

    @Override
    public void initialize() {
        super.initialize();
        log.info("Initializing");
    }
}