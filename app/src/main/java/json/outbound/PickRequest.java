package json.outbound;

public class PickRequest extends BaseRequest {
    private String pickWalkId;
    public PickRequest(String terminalId, String pickWalkId) {
        super(terminalId);
        this.pickWalkId = pickWalkId;
    }
}
