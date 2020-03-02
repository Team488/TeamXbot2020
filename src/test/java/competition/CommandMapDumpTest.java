package competition;

import org.junit.Test;

import competition.operator_interface.OperatorCommandMap;
import competition.operator_interface.OperatorInterface;
import xbot.common.controls.sensors.XXboxController;


public class CommandMapDumpTest extends BaseCompetitionTest {
    @Test
    public void testDumpCommandMap() {
        this.injector.getInstance(OperatorCommandMap.class);
        OperatorInterface oi = this.injector.getInstance(OperatorInterface.class);

        dumpGamepad(oi.operatorGamepad, "operatorGamepad");
        dumpGamepad(oi.driverGamepad, "driverGamepad");
        dumpGamepad(oi.manualOperatorGamepad, "manualOperatorGamepad");
        
    }

    public void dumpGamepad(XXboxController gamepad, String name) {
        System.out.println("Gamepad: " + name);
        gamepad.allocatedButtons.values().forEach((button) -> {
            if(button.command != null) {
                System.out.println("\t" + button.buttonName.name() + " -> " + button.command.getName());
            }
        });
    }
}