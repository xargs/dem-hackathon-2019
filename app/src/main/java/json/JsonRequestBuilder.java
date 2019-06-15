package json;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.util.UUID;

public class JsonRequestBuilder {

    /**
     * Build URL to register a user
     * @return
     */
    public String buildRegisterUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(JsonConstants.PTG_END_POINT);
        sb.append("/register");
        return sb.toString();
    }

    /**
     * Build URL for configuration request
     * @return
     */
    public String buildConfigurationRequestUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(JsonConstants.PTG_END_POINT);
        sb.append("/configurationRequest");
        return sb.toString();
    }

    /**
     * Generate UUID (Universally Unique IDentifier)
     * @return
     */
    public synchronized String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Build register request
     * @param terminalId
     * @return
     * @throws JSONException
     */
    public String buildRegisterRequest(String terminalId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", generateUUID());
        jsonObject.put("userName", JsonConstants.HACKER_10);
        jsonObject.put("terminalId", terminalId);
        return jsonObject.toString();
    }

    /**
     * Build configuration request.
     * @param terminalId
     * @return
     * @throws JSONException
     */
    public String buildConfigurationRequest(String terminalId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", generateUUID());
        jsonObject.put("userName", JsonConstants.HACKER_10);
        jsonObject.put("terminalId", terminalId);
        return jsonObject.toString();
    }
}
