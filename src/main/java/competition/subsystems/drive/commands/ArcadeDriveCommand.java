package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class ArcadeDriveCommand extends BaseCommand {

    final DriveSubsystem drive;
    final OperatorInterface oi;

    @Inject
    public ArcadeDriveCommand(DriveSubsystem drive, OperatorInterface oi) {
        this.drive = drive;
        this.oi = oi;
        this.addRequirements(drive);
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    @Override
    public void execute() {
        double translate = oi.gamepad.getLeftVector().y;
        translate = MathUtils.deadband(translate, oi.getJoystickDeadband());

        double rotate = oi.gamepad.getRightVector().x;
        rotate = MathUtils.deadband(rotate, oi.getJoystickDeadband());

        translate = MathUtils.exponentAndRetainSign(translate, 3);
        rotate = MathUtils.exponentAndRetainSign(rotate, 3);

        drive.arcadeDrive(translate, rotate);
    }

}