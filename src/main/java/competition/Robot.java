
package competition;

import competition.commandgroups.BasicAutonomousCommand;
import competition.operator_interface.OperatorCommandMap;
import competition.subsystems.SubsystemDefaultCommandMap;
import xbot.common.command.BaseRobot;

public class Robot extends BaseRobot {

    @Override
    protected void initializeSystems() {
        super.initializeSystems();
        this.injector.getInstance(SubsystemDefaultCommandMap.class);
        this.injector.getInstance(OperatorCommandMap.class);

        autonomousCommand = this.injector.getInstance(BasicAutonomousCommand.class);
    }

    @Override
    protected void setupInjectionModule() {
        this.injectionModule = new CompetitionModule(true);
    }
}
