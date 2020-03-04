
package competition;

import competition.autonomous.BasicAutonomousCommand;
import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import xbot.common.command.BaseRobot;

public class Robot extends BaseRobot {

    @Override
    protected void initializeSystems() {
        super.initializeSystems();
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);

        autonomousCommandSelector.setCurrentAutonomousCommand(this.injector.getInstance(BasicAutonomousCommand.class));
    }

    @Override
    protected void setupInjectionModule() {
        this.injectionModule = new CompetitionModule(true);
    }
}
