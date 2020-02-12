package competition.subsystems.lights;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.command.BaseSubsystem;
import xbot.common.controls.actuators.XRelay;
import xbot.common.injection.wpi_factories.CommonLibFactory;

@Singleton
public class LightsSubsystem extends BaseSubsystem {

    XRelay lightsOne;

    @Inject
    public LightsSubsystem(CommonLibFactory clf) {
        lightsOne = clf.createRelay(0);
    }

    public void setForward() {
        lightsOne.setForward();
    }

    public void setReverse() {
        lightsOne.setReverse();
    }

    public void stop() {
        lightsOne.stop();
    }
}