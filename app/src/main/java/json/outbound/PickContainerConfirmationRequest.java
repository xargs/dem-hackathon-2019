package json.outbound;

import java.util.List;

public class PickContainerConfirmationRequest extends BaseRequest {
    private String destinationLocationId;
    private List<String> pickContainerIds;
    public PickContainerConfirmationRequest(String terminalId, String destinationLocationId, List<String> pickContainerIds) {
        super(terminalId);
        this.destinationLocationId = destinationLocationId;
        this.pickContainerIds = pickContainerIds;
    }
}
