package json.inbound;

import java.util.List;

public class ConfigurationResponse extends BaseResponse {

    private boolean verifyPutLocation;
    private int verifyInventoryLabel;
    private List<String> allowedPickExceptions;
    private int verifyPickContainer;
    private boolean splitOnFull;
    private List<AllowedPickZones> allowedPickZones;
    private String pickingLocationId;

    public boolean isVerifyPutLocation() {
        return verifyPutLocation;
    }

    public int getVerifyInventoryLabel() {
        return verifyInventoryLabel;
    }

    public List<String> getAllowedPickExceptions() {
        return allowedPickExceptions;
    }

    public int getVerifyPickContainer() {
        return verifyPickContainer;
    }

    public boolean isSplitOnFull() {
        return splitOnFull;
    }

    public List<AllowedPickZones> getAllowedPickZones() {
        return allowedPickZones;
    }

    public String getPickingLocationId() {
        return pickingLocationId;
    }
}
