package competition;

public class ActualElectricalContract extends IdealElectricalContract {

    @Override
    public boolean isArmReady() {
        return false;
    }

    @Override
    public boolean isKickerReady() {
        return false;
    }

    @Override
    public boolean isIntakeReady() {
        return true;
    }

    @Override
    public boolean isHoodReady() {
        return false;
    }

    @Override
    public boolean isShooterWheelReady() {
        return false;
    }

    @Override
    public boolean isClimberReady() {
        return false;
    }

    @Override
    public boolean isCarouselReady() {
        return true;
    }

    @Override
    public boolean isTurretReady() {
        return false;
    }

    @Override
    public boolean isDriveReady() {
        return false;
    }
}