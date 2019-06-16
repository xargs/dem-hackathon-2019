package http;

import static json.JsonConstants.ASSIGN_PICK_CONTAINER_URL;
import static json.JsonConstants.CONFIGURATION_REQUEST_URL;
import static json.JsonConstants.CONFIRM_PICK_URL;
import static json.JsonConstants.PICK_CONTAINER_CONFIRMATION_URL;
import static json.JsonConstants.PICK_REQUEST_URL;
import static json.JsonConstants.PICK_WALK_FINISH_URL;
import static json.JsonConstants.PICK_WALK_REQUEST_URL;
import static json.JsonConstants.PTG_END_POINT;
import static json.JsonConstants.REGISTER_URL;
import static json.JsonConstants.SKU_IMAGES_URL;
import static json.JsonConstants.SKU_IMAGE_EXTENSION;

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

    /**
     * Get http connection for assign pick container request
     * @return
     */
    public static HttpConnection getAssignPickContainerRequestHttpConnection() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(PTG_END_POINT);
        sb.append(ASSIGN_PICK_CONTAINER_URL);
        return new HttpConnection(sb.toString());
    }

    /**
     * Get http connection for pick request
     * @return
     */
    public static HttpConnection getPickRequestHttpConnection() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(PTG_END_POINT);
        sb.append(PICK_REQUEST_URL);
        return new HttpConnection(sb.toString());
    }

    /**
     * Get http connection for confirm pick request
     * @return
     */
    public static HttpConnection getConfirmPickRequestHttpConnection() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(PTG_END_POINT);
        sb.append(CONFIRM_PICK_URL);
        return new HttpConnection(sb.toString());
    }

    /**
     * Get http connection for pick walk finish request
     * @return
     */
    public static HttpConnection getPickWalkFinishRequestHttpConnection() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(PTG_END_POINT);
        sb.append(PICK_WALK_FINISH_URL);
        return new HttpConnection(sb.toString());
    }

    /**
     * Get http connection for pick container confirmation request
     * @return
     */
    public static HttpConnection getPickContainerConfirmationRequestHttpConnection() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(PTG_END_POINT);
        sb.append(PICK_CONTAINER_CONFIRMATION_URL);
        return new HttpConnection(sb.toString());
    }

    /**
     * Get http connection for sku image request
     * @return
     */
    public static HttpConnection getSkuImageRequestHttpConnection(String sku) throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(SKU_IMAGES_URL);
        sb.append("/").append(sku).append(SKU_IMAGE_EXTENSION);
        return new HttpConnection(sb.toString());
    }
}
