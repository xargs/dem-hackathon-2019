package json.inbound;

public class BaseResponse {

    private String originalResponseGeneratedOn;
    private boolean isOriginalResponse;
    private int processTimeInMS;
    private int totalTimeInMS;
    private String messageKey;
    private String messageText;
    private String token;
    private StateCode stateCode;
    private String message;

    public String getOriginalResponseGeneratedOn() {
        return originalResponseGeneratedOn;
    }

    public boolean getIsOriginalResponse() {
        return isOriginalResponse;
    }

    public int getProcessTimeInMS() {
        return processTimeInMS;
    }

    public int getTotalTimeInMS() {
        return totalTimeInMS;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getToken() {
        return token;
    }

    public StateCode getStateCode() {
        return stateCode;
    }

    public String getMessage() {
        return message;
    }
}
