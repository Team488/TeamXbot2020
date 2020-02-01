package competition;

import xbot.common.injection.ElectricalContract;

public class IdealElectricalContract extends ElectricalContract {

    ///
    // Drive
    ///
    public DeviceInfo leftFrontDriveNeo() {
        return new DeviceInfo(32, false);
    }

    public DeviceInfo leftRearDriveNeo() {
        return new DeviceInfo(33, true);
    }

    public DeviceInfo rightFrontDriveNeo() {
        return new DeviceInfo(23, false);
    }

    public DeviceInfo rightRearDriveNeo() {
        return new DeviceInfo(22, false);
    }

    public DeviceInfo hoodMotor(){
        return new DeviceInfo(5, false);
    }

    public boolean isHoodReady(){
        return true;
    }
    
    public DeviceInfo conveyorMotor(){
        return new DeviceInfo(6, false);
    }

    public boolean isConveyorReady(){
        return true;
    }

    public DeviceInfo shooterWheelMotor(){
        return new DeviceInfo(7, false);
    }

    public boolean isShooterWheelReady(){
        return true;
    }


}