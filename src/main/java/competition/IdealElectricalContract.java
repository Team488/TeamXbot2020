package competition;

import xbot.common.injection.ElectricalContract;

public class IdealElectricalContract extends ElectricalContract {

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


    public DeviceInfo hoodMotor() {
        return new DeviceInfo(90, false); //Motor not real
    }

    public boolean isHoodReady() {
        return true;
    }
    
    public boolean isKickerReady(){
        return true;
    }

    public boolean isTurretReady() {
        return true;
    }

    public boolean isShooterWheelReady() {
        return true;
    }


    //Everything above sure about inversion
    //Everything below not sure about the inversion
    public DeviceInfo intakeMotor() {
        return new DeviceInfo(29, false);
    }

    public DeviceInfo shooterMotorMaster() {
        return new DeviceInfo(21, true);
    }
    
    public DeviceInfo shooterMotorFollower() {
        return new DeviceInfo(34, false);
    }

    public DeviceInfo climberMotorMaster() {
        return new DeviceInfo(35, false);
    }

    public DeviceInfo climberMotorfollower() {
        return new DeviceInfo(20, false);
    }

    public DeviceInfo turretMotor() {
        return new DeviceInfo(31, false);
    }

    public DeviceInfo kickerMotor() {
        return new DeviceInfo(24, false);
    }

    public DeviceInfo turretEncoder() {
        return new DeviceInfo(0, true);
    }

    public DeviceInfo carouselMotor() {
        return new DeviceInfo(35, false);
    }

    public boolean isCarouselReady() {
        return true;
    }

    public boolean isArmReady() {
        return true;
    }

    public boolean isFrontCollectingReady() {
        return true;
    }

    public DeviceInfo frontCollectingMotor() {
        return new DeviceInfo(6, true);
    }
    //i think this might be the same as intakeMotor but im not sure

    public boolean isHangingReady() { //same as climber
        return true;
    }

    public DeviceInfo hangerMotor() { //same as climber
        return new DeviceInfo(35, true);
    }

    public DeviceInfo getArmSolenoid() {
        return new DeviceInfo(1, true);
    }
}