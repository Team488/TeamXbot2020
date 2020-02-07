package competition;

public class ActualElectricalContract extends IdealElectricalContract {

    @Override
    public boolean isCollectorArmLiftingReady() {
        return false;
    }

	@Override
    public boolean isConveyorReady() {
        return false;
    }

    @Override
    public boolean isFrontCollectingReady() {
		return false;
	}

	@Overide
    public boolean isHoodReady() {
        return false;
    }
    
    @Override
    public boolean isShooterWheelReady() {
        return false;
    }

    @Override
    public boolean isHangingReady() {
		return false;
	}

	@Overide
    public boolean isCarouselReady() {
        return false;
    }
}