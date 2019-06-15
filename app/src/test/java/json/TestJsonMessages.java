package json;

import com.google.gson.Gson;

import junit.framework.Assert;

import org.junit.Test;

import json.inbound.ConfigurationResponse;
import json.inbound.PickResponse;
import json.inbound.PickWalkResponse;
import json.inbound.RegisterResponse;
import json.outbound.JsonRequestSender;

public class TestJsonMessages {

    private static final String TERMINAL_ID_KEY = "terminalId";

    @Test
    public void testRegisterRequest() throws Exception {
        String response = new JsonRequestSender().sendRegisterRequest(TERMINAL_ID_KEY);
        RegisterResponse registerResponse = new Gson().fromJson(response, RegisterResponse.class);
        Assert.assertTrue("messageText expected to be OK", registerResponse.getMessageText().equals("OK"));
    }

    @Test
    public void testConfigurationRequest() throws Exception {
        String response = new JsonRequestSender().sendConfigurationRequest(TERMINAL_ID_KEY);
        ConfigurationResponse configurationResponse = new Gson().fromJson(response, ConfigurationResponse.class);
        Assert.assertTrue("messageText expected to be OK", configurationResponse.getMessageText().equals("OK"));
        Assert.assertTrue("stateCode value expected to be OK", configurationResponse.getStateCode().getValue().equals("OK"));
    }

    @Test
    public void testPickWalkRequest() throws Exception {
        String response = new JsonRequestSender().sendPickWalkRequest(TERMINAL_ID_KEY);
        PickWalkResponse pickWalkResponse = new Gson().fromJson(response, PickWalkResponse.class);
        Assert.assertTrue("messageText expected to be OK", pickWalkResponse.getMessageText().equals("OK"));
        Assert.assertTrue("pickWalkAlreadyAssignedToWorker should be true", pickWalkResponse.isPickWalkAlreadyAssignedToWorker());
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
        Assert.assertTrue("Must have 1 pick", pickResponse.getPicks().size() >= 1);
    }
}
