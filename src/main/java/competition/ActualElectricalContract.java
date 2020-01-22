package competition;

public class ActualElectricalContract extends IdealElectricalContract {

    @Override
    public boolean isCollectorArmLiftingReady() {
        return false;
    }

    @Override
    public boolean isFrontCollectingReady() {
        return false;
    }
}