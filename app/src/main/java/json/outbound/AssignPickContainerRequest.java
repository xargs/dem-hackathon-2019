package json.outbound;

public class AssignPickContainerRequest extends BaseRequest {

    private String pickWalkId;
    private String pickContainerId;
    private String unitType;
    private String position;

    public AssignPickContainerRequest(String pickWalkId, String pickContainerId, String unitType, String position) {
        this.pickWalkId = pickWalkId;
        this.pickContainerId = pickContainerId;
        this.unitType = unitType;
        this.position = position;
    }
}
