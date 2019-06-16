package json;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;

import json.inbound.ConfigurationResponse;
import json.inbound.ConfirmPickResponse;
import json.inbound.Pick;
import json.inbound.PickResponse;
import json.inbound.PickWalkResponse;
import json.inbound.RegisterResponse;
import json.outbound.JsonRequestSender;

import static json.JsonConstants.CONFIRMATION_CODE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJsonMessages {

    private static final String TERMINAL_ID_KEY = "terminalId";

    @Test
    public void testRegisterRequest() throws Exception {
        String response = new JsonRequestSender().sendRegisterRequest(TERMINAL_ID_KEY);
        RegisterResponse registerResponse = new Gson().fromJson(response, RegisterResponse.class);
        assertEquals(true, registerResponse.getMessageText().equals("OK"), "messageText expected to be OK");
    }

    @Test
    public void testConfigurationRequest() throws Exception {
        String response = new JsonRequestSender().sendConfigurationRequest(TERMINAL_ID_KEY);
        ConfigurationResponse configurationResponse = new Gson().fromJson(response, ConfigurationResponse.class);
        assertEquals(true, configurationResponse.getMessageText().equals("OK"), "messageText expected to be OK");
        assertEquals(true, configurationResponse.getStateCode().getValue().equals("OK"), "stateCode value expected to be OK");
    }

    @Test
    public void testPickWalkRequest() throws Exception {
        String response = new JsonRequestSender().sendPickWalkRequest(TERMINAL_ID_KEY);
        PickWalkResponse pickWalkResponse = new Gson().fromJson(response, PickWalkResponse.class);
        assertEquals(true, pickWalkResponse.getMessageText().equals("OK"), "messageText expected to be OK");
        assertEquals(true, pickWalkResponse.isPickWalkAlreadyAssignedToWorker(), "pickWalkAlreadyAssignedToWorker should be true");
    }

    @Test
    public void testAssignPickContainerRequest() throws Exception {
        // TODO
    }

    @Test
    public void testPickRequest() throws Exception {
        String response = new JsonRequestSender().sendPickWalkRequest(TERMINAL_ID_KEY);
        PickWalkResponse pickWalkResponse = new Gson().fromJson(response, PickWalkResponse.class);
        String pickRequestResponse = new JsonRequestSender().sendPickRequest(TERMINAL_ID_KEY, pickWalkResponse.getPickWalkId());
        PickResponse pickResponse = new Gson().fromJson(pickRequestResponse, PickResponse.class);
        assertEquals(true, pickResponse.getPicks().size() >= 1, "Must have 1 pick");
    }

    @Test
    public void testConfirmPickRequest() throws Exception {
        String response = new JsonRequestSender().sendPickWalkRequest(TERMINAL_ID_KEY);
        PickWalkResponse pickWalkResponse = new Gson().fromJson(response, PickWalkResponse.class);
        String pickRequestResponse = new JsonRequestSender().sendPickRequest(TERMINAL_ID_KEY, pickWalkResponse.getPickWalkId());
        PickResponse pickResponse = new Gson().fromJson(pickRequestResponse, PickResponse.class);
        for(Pick pick : pickResponse.getPicks()) {
            String confirmPickResponse = new JsonRequestSender().sendConfirmPickRequest(TERMINAL_ID_KEY, CONFIRMATION_CODE, pick.getPrimaryKey(), pick.getQuantityTarget());
            ConfirmPickResponse confirmPickResponse1 = new Gson().fromJson(confirmPickResponse, ConfirmPickResponse.class);
            assertEquals(true, confirmPickResponse1.getMessageText().equals(CONFIRMATION_CODE), "messageText is expected to be OK");
        }
    }

    @Test
    public void testPickWalkFinishRequest() throws Exception {
        // TODO
    }

    @Test
    public void testPickContainerConfirmationRequest() throws Exception {
        // TODO
    }

    @Test
    public void testSkuRequest() throws Exception {
        String response = new JsonRequestSender().sendSkuImageRequest("7106804126");
        assertEquals(true, response != null, "Image not received");
    }
}
