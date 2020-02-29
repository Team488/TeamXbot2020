package competition.subsystems.drive.commands;

import com.google.inject.Inject;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.drive.DriveSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.math.MathUtils;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;


public class ArcadeDriveWithKinematicsCommand extends BaseCommand {

    final DriveSubsystem drive;
    final OperatorInterface oi;

    final DoubleProperty linearVelocityConstantValue;
    final DoubleProperty angularVelocityConstantValue;

    @Inject
    public ArcadeDriveWithKinematicsCommand(DriveSubsystem drive, OperatorInterface oi, PropertyFactory propManager) {
        this.drive = drive;
        this.oi = oi;
        this.addRequirements(drive);

        linearVelocityConstantValue = propManager.createPersistentProperty("Linear Velocity Constant Value", 200.0);
        angularVelocityConstantValue = propManager.createPersistentProperty("Angular Velocity Constant Value", 300.0);
        
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

        translate *= linearVelocityConstantValue.get();
        rotate *= angularVelocityConstantValue.get();

        drive.kinematicsDrive(translate, rotate);
    }

}