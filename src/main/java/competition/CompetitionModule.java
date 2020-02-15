package competition;

import competition.subsystems.pose.PoseSubsystem;
import xbot.common.injection.RobotModule;
import xbot.common.subsystems.pose.BasePoseSubsystem;

public class CompetitionModule extends RobotModule {

    boolean isPractice;
    
    public CompetitionModule(boolean isPractice) {
        this.isPractice = isPractice;
    }
    
    @Override
    protected void configure() {
        super.configure();
        this.bind(BasePoseSubsystem.class).to(PoseSubsystem.class);
        this.bind(IdealElectricalContract.class).to(ActualElectricalContract.class);
    }
}
