// package competition.subsystems.shooterwheel;

// import static org.junit.Assert.assertEquals;

// import org.junit.Test;

// import competition.BaseCompetitionTest;
// import competition.subsystems.shooterwheel.ShooterWheelSubsystem;
// import edu.wpi.first.wpilibj.Timer;

// public class TimedShooterWheelCommandTest extends BaseCompetitionTest {
//     @Test
//     public void testTimedShooting() {
//         ShooterWheelSubsystem shooterWheel = this.injector.getInstance(ShooterWheelSubsystem.class);
//         shooterWheel.goBackwards();

//         assertEquals(1, shooterWheel.leader.get(), 0.001);
//     }
// }