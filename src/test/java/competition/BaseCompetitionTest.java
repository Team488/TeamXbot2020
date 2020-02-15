package competition;

import competition.operator_interface.OperatorInterface;
import competition.subsystems.pose.PoseSubsystem;
import edu.wpi.first.networktables.NetworkTableInstance;
import xbot.common.injection.BaseWPITest;
import xbot.common.injection.ElectricalContract;
import xbot.common.injection.UnitTestModule;
import xbot.common.subsystems.pose.BasePoseSubsystem;

public class BaseCompetitionTest extends BaseWPITest {

    protected OperatorInterface oi;
    protected ElectricalContract contract;

    protected class TestModule extends UnitTestModule {
        @Override
        protected void configure() {
            super.configure();

            this.bind(ElectricalContract.class).to(IdealElectricalContract.class);
            this.bind(BasePoseSubsystem.class).to(PoseSubsystem.class);
            this.bind(NetworkTableInstance.class).toInstance(NetworkTableInstance.create());
        }
    }

    public BaseCompetitionTest() {
        guiceModule = new TestModule();
    }

    @Override
    public void setUp() {
        super.setUp();
        
        oi = injector.getInstance(OperatorInterface.class);
        contract = injector.getInstance(ElectricalContract.class);
    }
    
}