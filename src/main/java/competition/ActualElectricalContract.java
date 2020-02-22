package competition;

public class ActualElectricalContract extends IdealElectricalContract {

    @Override
    public boolean isArmReady() {
        return false;
    }

    @Override
    public boolean isKickerReady() {
        return true;
    }

    @Override
    public boolean isIntakeReady() {
        return false;
    }

    @Override
    public boolean isHoodReady() {
        return false;
    }

    @Override
    public boolean isShooterWheelReady() {
        return true;
    }

    @Override
    public boolean isHangingReady() {
        return false;
    }

    @Override
    public boolean isCarouselReady() {
        return false;
    }
}