package json;

import junit.framework.Assert;

import org.junit.Test;

import static json.JsonConstants.MESSAGE_TEXT_KEY;
import static json.JsonConstants.PICK_WALK_ALREADY_ASSIGNED_TO_WORKER;
import static json.JsonConstants.TERMINAL_ID_KEY;
import static json.JsonConstants.VALUE_KEY;

public class TestJsonMessages {

    @Test
    public void testRegisterRequest() throws Exception {
        String response = new JsonRequestSender().sendRegisterRequest(TERMINAL_ID_KEY);
        Object messageText = new JsonResponseParser().parseRegisterResponse(response, MESSAGE_TEXT_KEY);
        Assert.assertTrue("messageText expected to be OK", messageText.toString().equals("OK"));
    }

    @Test
    public void testConfigurationRequest() throws Exception {
        String response = new JsonRequestSender().sendConfigurationRequest(TERMINAL_ID_KEY);
        Object messageText = new JsonResponseParser().parseConfigurationResponse(response, MESSAGE_TEXT_KEY);
        Object value = new JsonResponseParser().parseConfigurationResponse(response, VALUE_KEY);
        Assert.assertTrue("messageText expected to be OK", messageText.toString().equals("OK"));
        Assert.assertTrue("stateCode value expected to be OK", value.toString().equals("OK"));
    }

    @Test
    public void testPickWalkRequest() throws Exception {
        String response = new JsonRequestSender().sendPickWalkRequest(TERMINAL_ID_KEY);
        Object messageText = new JsonResponseParser().parsePickWalkResponse(response, MESSAGE_TEXT_KEY);
        Object pickWalkAlreadyAssigned = new JsonResponseParser().parsePickWalkResponse(response, PICK_WALK_ALREADY_ASSIGNED_TO_WORKER);
        Assert.assertTrue("messageText expected to be OK", messageText.toString().equals("OK"));
        Assert.assertTrue("stateCode value expected to be OK", (boolean) pickWalkAlreadyAssigned);
    }
}
