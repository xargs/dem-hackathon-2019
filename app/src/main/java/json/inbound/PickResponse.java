package json.inbound;

import java.util.List;

public class PickResponse extends BaseResponse {
    private List<Pick> picks;

    public List<Pick> getPicks() {
        return picks;
    }
}
