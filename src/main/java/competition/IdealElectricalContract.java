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
        return new DeviceInfo(33, false);
    }

    public DeviceInfo rightFrontDriveNeo() {
        return new DeviceInfo(23, true);
    }

    public DeviceInfo rightRearDriveNeo() {
        return new DeviceInfo(22, true);
    }


}