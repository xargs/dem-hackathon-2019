package json.outbound;

import com.google.gson.Gson;

import java.util.List;


public class JsonRequestBuilder {

    /**
     * Build register request
     * @param terminalId
     * @return
     */
    public static String buildRegisterRequest(String terminalId) {
        return new Gson().toJson(new RegisterRequest(terminalId));
    }

    /**
     * Build deregister request
     * @param terminalId
     * @return
     */
    public static String buildDeregisterRequest(String terminalId) {
        return new Gson().toJson(new DeregisterRequest(terminalId));
    }

    /**
     * Build configuration request.
     * @param terminalId
     * @return
     */
    public static String buildConfigurationRequest(String terminalId) {
        return new Gson().toJson(new ConfigurationRequest(terminalId));
    }

    /**
     * Build pick walk request.
     * @param terminalId
     * @return
     */
    public static String buildPickWalkRequest(String terminalId) {
        return new Gson().toJson(new PickWalkRequest(terminalId));
    }

    /**
     * Build pick walk request.
     * @param terminalId
     * @param pickWalkId
     * @param pickContainerId
     * @param unitType
     * @param position
     * @return
     */
    public static String buildAssignPickContainerRequest(String terminalId, String pickWalkId, String pickContainerId, String unitType, String position) {
        return new Gson().toJson(new AssignPickContainerRequest(terminalId, pickContainerId, unitType, position));
    }

    /**
     * Build pick request.
     * @param terminalId
     * @param pickWalkId
     * @return
     */
    public static String buildPickRequest(String terminalId, String pickWalkId) {
        return new Gson().toJson(new PickRequest(terminalId, pickWalkId));
    }

    /**
     * Build confirm pick request.
     * @param terminalId
     * @param confirmationCode
     * @param primaryKey
     * @param picked
     * @return
     */
    public static String buildConfirmPickRequest(String terminalId, String confirmationCode, String primaryKey, int picked) {
        return new Gson().toJson(new ConfirmPickRequest(terminalId, confirmationCode, primaryKey, picked));
    }

    /**
     * Build pick walk finish request.
     * @param terminalId
     * @param pickWalkId
     * @return
     */
    public static String buildPickWalkFinishRequest(String terminalId, String pickWalkId) {
        return new Gson().toJson(new PickWalkFinishRequest(terminalId, pickWalkId));
    }

    /**
     * Build pick container confirmation request.
     * @param terminalId
     * @param destinationLocationId
     * @param pickContainerIds
     * @return
     */
    public static String buildPickContainerConfirmationRequest(String terminalId, String destinationLocationId, List<String> pickContainerIds ) {
        return new Gson().toJson(new PickContainerConfirmationRequest(terminalId, destinationLocationId, pickContainerIds));
    }
}
