package json.outbound;

import com.google.gson.Gson;

import org.json.JSONException;

import json.outbound.AssignPickContainerRequest;
import json.outbound.ConfigurationRequest;
import json.outbound.PickRequest;
import json.outbound.PickWalkRequest;
import json.outbound.RegisterRequest;


public class JsonRequestBuilder {

    /**
     * Build register request
     * @param terminalId
     * @return
     * @throws JSONException
     */
    public static String buildRegisterRequest(String terminalId) throws JSONException {
        Gson gson = new Gson();
        return gson.toJson(new RegisterRequest(terminalId));
    }

    /**
     * Build configuration request.
     * @param terminalId
     * @return
     * @throws JSONException
     */
    public static String buildConfigurationRequest(String terminalId) throws JSONException {
        Gson gson = new Gson();
        return gson.toJson(new ConfigurationRequest(terminalId));
    }

    /**
     * Build pick walk request.
     * @param terminalId
     * @return
     * @throws JSONException
     */
    public static String buildPickWalkRequest(String terminalId) throws JSONException {
        Gson gson = new Gson();
        return gson.toJson(new PickWalkRequest(terminalId));
    }

    /**
     * Build pick walk request.
     * @param terminalId
     * @param pickWalkId
     * @param pickContainerId
     * @param unitType
     * @param position
     * @return
     * @throws JSONException
     */
    public static String buildAssignPickContainerRequest(String terminalId, String pickWalkId, String pickContainerId, String unitType, String position) throws JSONException {
        Gson gson = new Gson();
        return gson.toJson(new AssignPickContainerRequest(terminalId, pickContainerId, unitType, position));
    }

    /**
     * Build pick request.
     * @param terminalId
     * @param pickWalkId
     * @return
     * @throws JSONException
     */
    public static String buildPickRequest(String terminalId, String pickWalkId) throws JSONException {
        Gson gson = new Gson();
        return gson.toJson(new PickRequest(terminalId, pickWalkId));
    }
}
