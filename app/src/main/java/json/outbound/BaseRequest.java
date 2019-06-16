package json.outbound;

import java.util.UUID;

import json.JsonConstants;

public class BaseRequest {

    private String uuid;
    private String userName;
    private String terminalId;

    public BaseRequest() {
        this.uuid = UUID.randomUUID().toString();
        this.userName = JsonConstants.HACKER_10;
        this.terminalId = JsonRequestBuilder.getTerminalId();
    }
}
