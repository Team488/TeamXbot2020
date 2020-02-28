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

    double linearVelocityConstant = 1; // needs tuning
    double angularVelocityConstant = 1; // needs tuning

    @Inject
    public ArcadeDriveWithKinematicsCommand(DriveSubsystem drive, OperatorInterface oi, PropertyFactory propManager) {
        this.drive = drive;
        this.oi = oi;
        this.addRequirements(drive);

        linearVelocityConstantValue = propManager.createEphemeralProperty("Linear Velocity Constant Value", 0.0);
        angularVelocityConstantValue = propManager.createEphemeralProperty("Angular Velocity Constant Value", 0.0);
        
    }

    @Override
    public void initialize() {
        log.info("Initializing");
    }

    // Functions for robot tuning
    public void increaseLinearVelocityConstant(double increaseRate) {
        linearVelocityConstant += increaseRate;
        linearVelocityConstantValue.set(linearVelocityConstant);
        log.info("Linear Velocity Constant Increased by " + increaseRate + ": " + linearVelocityConstant);
    }

    public void decreaseLinearVelocityConstant(double decreaseRate) {
        linearVelocityConstant -= decreaseRate;
        linearVelocityConstantValue.set(linearVelocityConstant);
        log.info("Linear Velocity Constant Decreased by " + decreaseRate + ": " + linearVelocityConstant);
    }

    public void increaseAngularVelocityConstant(double increaseRate) {
        angularVelocityConstant += increaseRate;
        angularVelocityConstantValue.set(angularVelocityConstant);
        log.info("Angular Velocity Constant Increased by " + increaseRate + ": " + linearVelocityConstant);
    }
    
    public void decreaseAngularVelocityConstant(double decreaseRate) {
        angularVelocityConstant -= decreaseRate;
        angularVelocityConstantValue.set(angularVelocityConstant);
        log.info("Angular Velocity Constant Decreased by " + decreaseRate + ": " + linearVelocityConstant);
    }

    @Override
    public void execute() {
        double translate = oi.driverGamepad.getLeftVector().y;
        translate = MathUtils.deadband(translate, oi.getJoystickDeadband());

        double rotate = oi.driverGamepad.getRightVector().x;
        rotate = MathUtils.deadband(rotate, oi.getJoystickDeadband());

        translate = MathUtils.exponentAndRetainSign(translate, 3);
        rotate = MathUtils.exponentAndRetainSign(rotate, 3);

        translate *= linearVelocityConstant;
        rotate *= angularVelocityConstant;

        drive.kinematicsDrive(translate, rotate);
    }

}