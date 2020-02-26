package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;

public class ArcadeDriveWithKinematicsCommand extends BaseCommand {

    final DriveSubsystem drive;
    final OperatorInterface oi;

    final double linearVelocityConstant = 0;
    final double angularVelocityConstant = 0;

    @Inject
    public ArcadeDriveWithKinematicsCommand(DriveSubsystem drive, OperatorInterface oi) {
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
        double translate = oi.driverGamepad.getLeftVector().y;
        translate = MathUtils.deadband(translate, oi.getJoystickDeadband());

        double rotate = oi.driverGamepad.getRightVector().x;
        rotate = MathUtils.deadband(rotate, oi.getJoystickDeadband());

        translate = MathUtils.exponentAndRetainSign(translate, 3);
        rotate = MathUtils.exponentAndRetainSign(rotate, 3);

         // multiplied by a constant for kinematics drive
        translate *= linearVelocityConstant;
        rotate *= angularVelocityConstant;

        drive.kinematicsDrive(translate, rotate);
    }

}