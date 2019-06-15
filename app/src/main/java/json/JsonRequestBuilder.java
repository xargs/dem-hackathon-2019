package json;

import org.json.JSONException;
import org.json.simple.JSONObject;

import java.util.UUID;

import static json.JsonConstants.HACKER_10;
import static json.JsonConstants.TERMINAL_ID_KEY;
import static json.JsonConstants.USER_NAME_KEY;
import static json.JsonConstants.UUID_KEY;

public class JsonRequestBuilder {

    /**
     * Generate UUID (Universally Unique IDentifier)
     * @return
     */
    public synchronized static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * Build register request
     * @param terminalId
     * @return
     * @throws JSONException
     */
    public static String buildRegisterRequest(String terminalId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UUID_KEY, generateUUID());
        jsonObject.put(USER_NAME_KEY, HACKER_10);
        jsonObject.put(TERMINAL_ID_KEY, terminalId);
        return jsonObject.toString();
    }

    /**
     * Build configuration request.
     * @param terminalId
     * @return
     * @throws JSONException
     */
    public static String buildConfigurationRequest(String terminalId) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(UUID_KEY, generateUUID());
        jsonObject.put(USER_NAME_KEY, HACKER_10);
        jsonObject.put(TERMINAL_ID_KEY, terminalId);
        return jsonObject.toString();
    }
}
