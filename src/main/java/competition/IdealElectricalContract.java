package competition;

import xbot.common.injection.ElectricalContract;

public class IdealElectricalContract extends ElectricalContract {

    ///
    // Drive
    ///
    public DeviceInfo leftFrontDriveNeo() {
        return new DeviceInfo(32, true);
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

    //Everything above sure about inversion
    //Everything below not sure about the inversion
    public DeviceInfo intakeMotor()
    {
        return new DeviceInfo(24, false);
    }

    public DeviceInfo shooterMotorMaster()
    {
        return new DeviceInfo(21, false);
    }
    public DeviceInfo shooterMotorFollower()
    {
        return new DeviceInfo(34, false);
    }

    public DeviceInfo climberMotorMaster()
    {
        return new DeviceInfo(35, false);
    }
    public DeviceInfo climberMotorfollower()
    {
        return new DeviceInfo(20, false);
    }

    public DeviceInfo rotationMotor()
    {
        return new DeviceInfo(31, false);
    }

    public DeviceInfo carouselMotor()
    {
        return new DeviceInfo(25, false);
    }

}