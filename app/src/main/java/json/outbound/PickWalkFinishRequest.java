package json.outbound;

public class PickWalkFinishRequest extends BaseRequest {
    private String pickWalkId;
    public PickWalkFinishRequest(String terminalId, String pickWalkId) {
        super(terminalId);
        this.pickWalkId = pickWalkId;
    }
}
