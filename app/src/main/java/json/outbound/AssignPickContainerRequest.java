package json.outbound;

public class AssignPickContainerRequest extends BaseRequest {

    private String pickWalkId;
    private String pickContainerId;
    private String unitType;
    private String position;

    public AssignPickContainerRequest(String terminalId, String pickContainerId, String unitType, String position) {
        super(terminalId);
        this.pickContainerId = pickContainerId;
        this.unitType = unitType;
        this.position = position;
    }
}
