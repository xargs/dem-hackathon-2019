package json.outbound;

import com.google.gson.Gson;

import org.apache.log4j.Logger;

import java.net.InetAddress;
import java.util.List;


public class JsonRequestBuilder {

    private static final Logger logger = Logger.getLogger(JsonRequestBuilder.class);

    public static String getTerminalId() {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            return ip.getHostName();
        } catch(Exception e) {
            logger.error("getTerminalId", e);
        }
        return "terminalId";
    }

    /**
     * Build register request
     * @return
     */
    public static String buildRegisterRequest() {
        return new Gson().toJson(new RegisterRequest());
    }

    /**
     * Build deregister request
     * @return
     */
    public static String buildDeregisterRequest() {
        return new Gson().toJson(new DeregisterRequest());
    }

    /**
     * Build configuration request.
     * @return
     */
    public static String buildConfigurationRequest() {
        return new Gson().toJson(new ConfigurationRequest());
    }

    /**
     * Build pick walk request.
     * @return
     */
    public static String buildPickWalkRequest() {
        return new Gson().toJson(new PickWalkRequest());
    }

    /**
     * Build pick walk request.
     * @param pickWalkId
     * @param pickContainerId
     * @param unitType
     * @param position
     * @return
     */
    public static String buildAssignPickContainerRequest(String pickWalkId, String pickContainerId, String unitType, String position) {
        return new Gson().toJson(new AssignPickContainerRequest(pickWalkId, pickContainerId, unitType, position));
    }

    /**
     * Build pick request.
     * @param pickWalkId
     * @return
     */
    public static String buildPickRequest(String pickWalkId) {
        return new Gson().toJson(new PickRequest(pickWalkId));
    }

    /**
     * Build confirm pick request.
     * @param confirmationCode
     * @param primaryKey
     * @param picked
     * @return
     */
    public static String buildConfirmPickRequest(String confirmationCode, String primaryKey, int picked) {
        return new Gson().toJson(new ConfirmPickRequest(confirmationCode, primaryKey, picked));
    }

    /**
     * Build pick walk finish request.
     * @param pickWalkId
     * @return
     */
    public static String buildPickWalkFinishRequest(String pickWalkId) {
        return new Gson().toJson(new PickWalkFinishRequest(pickWalkId));
    }

    /**
     * Build pick container confirmation request.
     * @param destinationLocationId
     * @param pickContainerIds
     * @return
     */
    public static String buildPickContainerConfirmationRequest(String destinationLocationId, List<String> pickContainerIds ) {
        return new Gson().toJson(new PickContainerConfirmationRequest(destinationLocationId, pickContainerIds));
    }
}
