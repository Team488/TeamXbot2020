package competition.subsystems.climber;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import competition.BaseCompetitionTest;
import competition.operator_interface.OperatorInterface;
import competition.subsystems.climber.commands.ClimberViaTriggerCommand;
import edu.wpi.first.wpilibj.MockXboxControllerAdapter;

public class ClimberViaTriggerCommandTest extends BaseCompetitionTest {

    @Test
    public void testRightTrigger(){
        ClimberSubsystem climber = this.injector.getInstance(ClimberSubsystem.class);
        ClimberViaTriggerCommand command = this.injector.getInstance(ClimberViaTriggerCommand.class);
        OperatorInterface oi = this.injector.getInstance(OperatorInterface.class);
        MockXboxControllerAdapter mock = ((MockXboxControllerAdapter)oi.operatorGamepad);
        
        mock.setLeftTrigger(0.0); // 0.5
        mock.setRightTrigger(0.5); // 0.6

        // Checking if trigger buttons are working        
        assertTrue("Should be pressing on right trigger", mock.getRightTrigger() >= 0.1);

        command.initialize();
        command.execute();

        //Checks if leader motor is using the correct amount of power
        assertEquals(-0.5, climber.leftMotor.get(), 0.001);
    }

    @Test
    public void testLeftTrigger(){
        ClimberSubsystem climber = this.injector.getInstance(ClimberSubsystem.class);
        ClimberViaTriggerCommand command = this.injector.getInstance(ClimberViaTriggerCommand.class);
        OperatorInterface oi = this.injector.getInstance(OperatorInterface.class);
        MockXboxControllerAdapter mock = ((MockXboxControllerAdapter)oi.operatorGamepad);

        mock.setLeftTrigger(0.5);
        mock.setRightTrigger(0.0);
        
        // Checking if the trigger buttons are working
        assertTrue("Should be pressing on left trigger", mock.getLeftTrigger() >= 0.1);

        command.initialize();
        command.execute();

        //Checks if leader motor is using the correct amount of power
        assertEquals(0.5, climber.rightMotor.get(), 0.001);

    }

    @Test
    public void testBothTriggers(){
        ClimberSubsystem climber = this.injector.getInstance(ClimberSubsystem.class);
        ClimberViaTriggerCommand command = this.injector.getInstance(ClimberViaTriggerCommand.class);
        OperatorInterface oi = this.injector.getInstance(OperatorInterface.class);
        MockXboxControllerAdapter mock = ((MockXboxControllerAdapter)oi.operatorGamepad);

        mock.setLeftTrigger(0.5);
        mock.setRightTrigger(0.5);

        assertTrue("Should be pressing on left trigger", mock.getLeftTrigger() >= 0.1);
        assertTrue("Should be pressing on right trigger", mock.getRightTrigger() >= 0.1);

        command.initialize();
        command.execute();

        assertEquals(0.0, climber.leftMotor.get(), 0.001);
    }

}

