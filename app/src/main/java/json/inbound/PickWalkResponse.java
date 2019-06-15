package json.inbound;

import java.util.List;

public class PickWalkResponse extends BaseResponse {

    private String pickWalkId;
    private boolean pickWalkAlreadyAssignedToWorker;
    private int noOfPickContainer;
    private String defaultPickContainerType;
    private List<PickContainer> pickContainers;
    private boolean splitOnFull;

    public String getPickWalkId() {
        return pickWalkId;
    }

    public boolean isPickWalkAlreadyAssignedToWorker() {
        return pickWalkAlreadyAssignedToWorker;
    }

    public int getNoOfPickContainer() {
        return noOfPickContainer;
    }

    public String getDefaultPickContainerType() {
        return defaultPickContainerType;
    }

    public List<PickContainer> getPickContainers() {
        return pickContainers;
    }

    public boolean isSplitOnFull() {
        return splitOnFull;
    }
}
