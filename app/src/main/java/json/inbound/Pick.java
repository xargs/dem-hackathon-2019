package json.inbound;

public class Pick {

    private String primaryKey;
    private int sequence;
    private String coordinate;
    private String checkDigit;
    private int quantityTarget;
    private String quantityUnit;
    private String skuId;
    private String skuDescription;
    private String orderId;
    private String orderPos;
    private String destinationId;
    private String destinationCheckDigit;
    private boolean reverseRecommended;

    public String getPrimaryKey() {
        return primaryKey;
    }

    public int getSequence() {
        return sequence;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public String getCheckDigit() {
        return checkDigit;
    }

    public int getQuantityTarget() {
        return quantityTarget;
    }

    public String getQuantityUnit() {
        return quantityUnit;
    }

    public String getSkuId() {
        return skuId;
    }

    public String getSkuDescription() {
        return skuDescription;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getOrderPos() {
        return orderPos;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public String getDestinationCheckDigit() {
        return destinationCheckDigit;
    }

    public boolean isReverseRecommended() {
        return reverseRecommended;
    }
}
