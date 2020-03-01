package competition.operator_interface;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import xbot.common.controls.sensors.XXboxController;
import xbot.common.injection.wpi_factories.CommonLibFactory;
import xbot.common.injection.wpi_factories.DevicePolice;
import xbot.common.logging.RobotAssertionManager;
import xbot.common.properties.DoubleProperty;
import xbot.common.properties.IPropertySupport;
import xbot.common.properties.PropertyFactory;

/**
 * This class is the glue that binds the controls on the physical operator interface to the commands and command groups
 * that allow control of the robot.
 */
@Singleton
public class OperatorInterface implements IPropertySupport {
  
    public XXboxController driverGamepad;
    public XXboxController operatorGamepad;
    public XXboxController manualOperatorGamepad;
    final DoubleProperty joystickDeadband;

    @Inject
    public OperatorInterface(CommonLibFactory factory, PropertyFactory pf, RobotAssertionManager manager, DevicePolice police) {
        pf.setPrefix(this);
        joystickDeadband = pf.createPersistentProperty("JoystickDeadband", 0.08);

        driverGamepad = factory.createXboxController(0);
        driverGamepad.setLeftInversion(false, true);
        driverGamepad.setRightInversion(true, true);

        operatorGamepad = factory.createXboxController(1);
        operatorGamepad.setLeftInversion(true, true);
        operatorGamepad.setRightInversion(true, true);

        manualOperatorGamepad = factory.createXboxController(2);
        manualOperatorGamepad.setLeftInversion(false, true);
        manualOperatorGamepad.setRightInversion(true, true);
    }
    
    public double getJoystickDeadband() {
        return joystickDeadband.get();
    }
    
    @Override
    public String getPrefix() {
        return "OperatorInterface";
    }
}
