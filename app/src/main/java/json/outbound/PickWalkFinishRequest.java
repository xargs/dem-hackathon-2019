package json.outbound;

public class PickWalkFinishRequest extends BaseRequest {
    private String pickWalkId;
    public PickWalkFinishRequest(String pickWalkId) {
        this.pickWalkId = pickWalkId;
    }
}
