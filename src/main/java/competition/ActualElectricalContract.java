package competition;

public class ActualElectricalContract extends IdealElectricalContract {

    @Override
    public boolean isCollectorArmLiftingReady() {
        return false;
    }

    @Override
    public boolean isIndexerReady() {
        return false;
    }

    @Override
    public boolean isFrontCollectingReady() {
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