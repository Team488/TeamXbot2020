
package competition;

import java.util.List;

import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import competition.subsystems.drive.DriveSubsystem;
import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import xbot.common.command.BaseRobot;

public class Robot extends BaseRobot {

    @Override
    protected void initializeSystems() {
        super.initializeSystems();
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);
        registerPeriodicDataSource(this.injector.getInstance(PoseSubsystem.class));
    }

    @Override
    protected void setupInjectionModule() {
        this.injectionModule = new CompetitionModule(true);
    }

    @Override
    public void autonomousInit() {
        // base will run the command
        DriveSubsystem drive = injector.getInstance(DriveSubsystem.class);
        PoseSubsystem pose = injector.getInstance(PoseSubsystem.class);

        drive.resetEncoders();
        pose.resetDistanceTraveled();

        var autoVoltageConstraint =
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(1.01, //DriveConstants.ksVolts,
                                       0.0679, //DriveConstants.kvVoltSecondsPerMeter,
                                       0.0143), //DriveConstants.kaVoltSecondsSquaredPerMeter),
                                       new DifferentialDriveKinematics(26.164),
            10);

        TrajectoryConfig config =
        new TrajectoryConfig(100,//max inches per second: AutoConstants.kMaxSpeedMetersPerSecond,
                             100) //AutoConstants.kMaxAccelerationMetersPerSecondSquared)
            // Add kinematics to ensure max speed is actually obeyed
            .setKinematics(new DifferentialDriveKinematics(26.164))
            // Apply the voltage constraint
            .addConstraint(autoVoltageConstraint);

        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
            // Start at the origin facing the +X direction
            new Pose2d(0, 0, new Rotation2d(Math.toRadians(90))),
            // Pass through these two interior waypoints, making an 's' curve path
            List.of(
                new Translation2d(-1*36, 1*36),
                new Translation2d(1*36, 2*36)
            ),
            // End 3 meters straight ahead of where we started, facing forward
            new Pose2d(0, 3*36, new Rotation2d(Math.toRadians(90))),
            // Pass config
            config
        );

        RamseteCommand rc = new RamseteCommand(
            exampleTrajectory,
            pose::getFancyPose,
            new RamseteController(),
            new SimpleMotorFeedforward(1.01, 0.0679, 0.0143),
            new DifferentialDriveKinematics(26.164),
            drive::getWheelSpeeds,
            new PIDController(0.681, 0, 0),
            new PIDController(0.681, 0, 0),
            drive::setVolts,
            drive);

            autonomousCommand = rc;


        super.autonomousInit();
    }
}
