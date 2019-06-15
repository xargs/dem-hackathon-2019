package json.inbound;

import java.util.List;

public class PickWalkFinishResponse extends BaseResponse {
    private List<FinishItem> finishItems;

    public List<FinishItem> getFinishItems() {
        return finishItems;
    }
}
