package http;

import static json.JsonConstants.CONFIGURATION_REQUEST_URL;
import static json.JsonConstants.PICK_WALK_REQUEST_URL;
import static json.JsonConstants.PTG_END_POINT;
import static json.JsonConstants.REGISTER_URL;

public class HttpConnectionFactory {

    /**
     * Get http connection for register
     * @return
     */
    public static HttpConnection getRegisterHttpConnection() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(PTG_END_POINT);
        sb.append(REGISTER_URL);
        return new HttpConnection(sb.toString());
    }

    /**
     * Get http connection for configuration request
     * @return
     */
    public static HttpConnection getConfigurationHttpConnection() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(PTG_END_POINT);
        sb.append(CONFIGURATION_REQUEST_URL);
        return new HttpConnection(sb.toString());
    }

    /**
     * Get http connection for pick walk request
     * @return
     */
    public static HttpConnection getPickWalkRequestHttpConnection() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(PTG_END_POINT);
        sb.append(PICK_WALK_REQUEST_URL);
        return new HttpConnection(sb.toString());
    }
}
