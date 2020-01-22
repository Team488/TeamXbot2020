package competition;

import xbot.common.injection.ElectricalContract;

public class IdealElectricalContract extends ElectricalContract {

    ///
    // Drive
    ///
    public DeviceInfo leftFrontDriveNeo() {
        return new DeviceInfo(1, false);
    }

    public DeviceInfo leftRearDriveNeo() {
        return new DeviceInfo(2, false);
    }

    public DeviceInfo rightFrontDriveNeo() {
        return new DeviceInfo(3, true);
    }

    public DeviceInfo rightRearDriveNeo() {
        return new DeviceInfo(4, true);
    }


}