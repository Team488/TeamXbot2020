package competition.subsystems.intake.commands;

import com.google.inject.Inject;

import competition.subsystems.intake.IntakeSubsystem;
import xbot.common.command.BaseCommand;
import xbot.common.logic.TimeStableValidator;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.PropertyFactory;

public class IntakeStutterCommand extends BaseCommand {

    final DoubleProperty intakeRunTimeProp;
    final DoubleProperty intakeStopTimeProp;

    final TimeStableValidator runningValidator;
    final TimeStableValidator stopValidator;

    final IntakeSubsystem intake;

    State state;

    enum State {
        Running,
        Stopped
    }

    @Inject
    public IntakeStutterCommand(IntakeSubsystem intake, PropertyFactory pf) {
        pf.setPrefix(this);

        addRequirements(intake);
        this.intake = intake;

        intakeRunTimeProp = pf.createEphemeralProperty("Intake run time seconds", 0.8);
        intakeStopTimeProp = pf.createEphemeralProperty("Intake stop time seconds", 0.2);
        
        runningValidator = new TimeStableValidator(() -> intakeRunTimeProp.get());
        stopValidator = new TimeStableValidator(() -> intakeStopTimeProp.get());
    }

    @Override
    public void initialize() {
        log.info("Initializing");
        state = State.Running;
    }

    @Override
    public void execute() {
        updateState();

        switch (state) {
        case Running:
            intake.intake();
            break;
        case Stopped:
            intake.stop();
            break;
        default:
            break;
        }
    }

    private void updateState() {
        switch (state) {
        case Running:
            boolean runStable = runningValidator.checkStable(true);
            stopValidator.checkStable(false);
            if (runStable) {
                state = State.Stopped;
                stopValidator.checkStable(true);
            }
            break;
        case Stopped:
            boolean stopStable = stopValidator.checkStable(true);
            runningValidator.checkStable(false);
            if (stopStable) {
                state = State.Running;
                runningValidator.checkStable(true);
            }
            break;
        default:
            break;
        }
    }
}