package competition.commandgroups;

import java.util.function.Supplier;

import com.google.inject.Inject;

import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.internalconveyor.KickerSubsystem;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.turret.TurretSubsystem;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import xbot.common.command.SimpleWaitForMaintainerCommand;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class PrepareToFireCommand extends ParallelCommandGroup {

    private final DoubleProperty waitTimeProp;
    Supplier<Double> externalWaitSupplier;

    @Inject
    PrepareToFireCommand(ShooterWheelSubsystem wheel, TurretSubsystem turret, HoodSubsystem hood,
            KickerSubsystem kicker, PropertyFactory pf) {
        waitTimeProp = pf.createPersistentProperty("Max Wait Time", 3);

        this.addCommands(
            new SimpleWaitForMaintainerCommand(wheel, getWaitTime()),
            new SimpleWaitForMaintainerCommand(turret, getWaitTime()),
            new SimpleWaitForMaintainerCommand(hood, getWaitTime()), 
            new InstantCommand(kicker::lift, kicker));
    }

    public void setWaitTime(Supplier<Double> externalWaitSupplier) {
        this.externalWaitSupplier = externalWaitSupplier;
    }

    private Supplier<Double> getWaitTime() {
        if (externalWaitSupplier == null) {
            return () -> waitTimeProp.get();
        }
        return externalWaitSupplier;
    }
}