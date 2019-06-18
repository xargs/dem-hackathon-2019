package json.inbound;

import java.io.Serializable;

public class Pick implements Serializable {

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
    private String pickWalkId;

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

    public String getPickWalkId() {
        return pickWalkId;
    }

    public void setPickWalkId(String pickWalkId) {
        this.pickWalkId = pickWalkId;
    }

    public void setPrimaryKey(String primaryKey) {
        this.primaryKey = primaryKey;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public void setCheckDigit(String checkDigit) {
        this.checkDigit = checkDigit;
    }

    public void setQuantityTarget(int quantityTarget) {
        this.quantityTarget = quantityTarget;
    }

    public void setQuantityUnit(String quantityUnit) {
        this.quantityUnit = quantityUnit;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public void setSkuDescription(String skuDescription) {
        this.skuDescription = skuDescription;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderPos(String orderPos) {
        this.orderPos = orderPos;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public void setDestinationCheckDigit(String destinationCheckDigit) {
        this.destinationCheckDigit = destinationCheckDigit;
    }

    public void setReverseRecommended(boolean reverseRecommended) {
        this.reverseRecommended = reverseRecommended;
    }
}
