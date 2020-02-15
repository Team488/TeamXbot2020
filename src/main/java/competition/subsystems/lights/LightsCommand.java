package competition.subsystems.lights;

import com.google.inject.Inject;

import xbot.common.command.BaseCommand;

public class LightsCommand extends BaseCommand {

    final LightsSubsystem lights;
    boolean on = false;

    @Inject
    public LightsCommand(LightsSubsystem lights) {
        this.lights = lights;
        this.addRequirements(lights);
    }

    public void setOn(boolean on) {
        this.on = on;
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        if (this.on) {
            log.info("Setting On");
            lights.setForward();
        } else {
            log.info("Setting off");
            lights.stop();
        }
    }

    @Override
    public void execute() {
    }
}