package json.outbound;

public class ConfirmPickRequest extends BaseRequest {
    private String confirmationCode;
    private String primaryKey;
    private int picked;

    public ConfirmPickRequest(String terminalId, String confirmationCode, String primaryKey, int picked) {
        super(terminalId);
        this.confirmationCode = confirmationCode;
        this.primaryKey = primaryKey;
        this.picked = picked;
    }
}
