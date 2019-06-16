package json.outbound;

public class PickRequest extends BaseRequest {
    private String pickWalkId;
    public PickRequest(String pickWalkId) {
        this.pickWalkId = pickWalkId;
    }
}
