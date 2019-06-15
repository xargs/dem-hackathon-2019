package json.inbound;

public class FinishItem {

    private String pickContainerId;
    private String destinationCoordinate;
    private String destinationCoordinateCheckDigit;
    private int destinationPosition;
    private String destinationLocationId;

    public String getPickContainerId() {
        return pickContainerId;
    }

    public String getDestinationCoordinate() {
        return destinationCoordinate;
    }

    public String getDestinationCoordinateCheckDigit() {
        return destinationCoordinateCheckDigit;
    }

    public int getDestinationPosition() {
        return destinationPosition;
    }

    public String getDestinationLocationId() {
        return destinationLocationId;
    }
}
