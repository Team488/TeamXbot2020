package competition.subsystems;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import competition.subsystems.arm.ArmSubsystem;
import competition.subsystems.carousel.CarouselSubsystem;
import competition.subsystems.carousel.commands.CarouselViaJoystickCommand;
import competition.subsystems.climber.ClimberSubsystem;
import competition.subsystems.climber.commands.ClimberViaTriggerCommand;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.drive.commands.ArcadeDriveCommand;
import competition.subsystems.hood.HoodSubsystem;
import competition.subsystems.hood.commands.HoodMaintainerCommand;
import competition.subsystems.intake.IntakeSubsystem;
import competition.subsystems.intake.commands.CollectViaTriggersCommand;
import competition.subsystems.internalconveyor.KickerSubsystem;
import competition.subsystems.internalconveyor.commands.KickerViaTriggerCommand;
import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
import competition.subsystems.shooterwheel.commands.ShooterWheelMaintainerCommand;
import competition.subsystems.turret.TurretSubsystem;
import competition.subsystems.turret.commands.TurretMaintainerCommand;
import xbot.common.command.XScheduler;

@Singleton
public class SubsystemDefaultCommandMap {
    // For setting the default commands on subsystems
    @Inject
    public void setupDriveSubsystem(XScheduler scheduler, DriveSubsystem drive, ArcadeDriveCommand command) {
        scheduler.setDefaultCommand(drive, command);
    }

    @Inject
    public void setupTurretSubsystem(XScheduler scheduler, TurretSubsystem tSub, TurretMaintainerCommand maintain)
    {
        scheduler.setDefaultCommand(tSub, maintain);
    }

    @Inject
    public void setupCarouselSubsystem(XScheduler scheduler, CarouselSubsystem carousel, CarouselViaJoystickCommand command) {
        scheduler.setDefaultCommand(carousel, command);
    }

    @Inject
    public void setupHoodSubsystem(XScheduler scheduler, HoodSubsystem hood, HoodMaintainerCommand command) {
        scheduler.setDefaultCommand(hood, command);
    }

    @Inject
    public void setupArmSubsystem(XScheduler scheduler, ArmSubsystem arm) {
        // NEVER SET A DEFAULT ARM COMMAND!!
    }

    @Inject
    public void setupIntakeSubsystem(XScheduler scheduler, IntakeSubsystem intake, CollectViaTriggersCommand command) {
        scheduler.setDefaultCommand(intake, command);
    }

    @Inject
    public void setupShooterWheelSubsystem(XScheduler scheduler, ShooterWheelSubsystem shooterWheel, ShooterWheelMaintainerCommand command) {
        scheduler.setDefaultCommand(shooterWheel, command);
    }

    @Inject
    public void setupKicker(XScheduler scheduler, KickerSubsystem kicker, KickerViaTriggerCommand trigger) {
        scheduler.setDefaultCommand(kicker, trigger);
    }

    @Inject
    public void setupClimbSubsystem(XScheduler scheduler, ClimberSubsystem climber, ClimberViaTriggerCommand command){
        scheduler.setDefaultCommand(climber, command);
    }
}
