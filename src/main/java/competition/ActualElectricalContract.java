package competition;

public class ActualElectricalContract extends IdealElectricalContract {

    @Override
    public boolean isConveyorReady() {
        return false;
    }
    @Override
    public boolean isHoodReady() {
        return false;
    }
    
    @Override
    public boolean isShooterWheelReady() {
        return false;
    }
}