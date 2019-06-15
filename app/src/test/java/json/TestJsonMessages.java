package json;

import junit.framework.Assert;

import org.junit.Test;

import static json.JsonConstants.MESSAGE_TEXT_KEY;
import static json.JsonConstants.TERMINAL_ID_KEY;
import static json.JsonConstants.VALUE_KEY;

public class TestJsonMessages {

    @Test
    public void testRegisterRequest() throws Exception {
        String response = new JsonRequestSender().sendRegisterRequest(TERMINAL_ID_KEY);
        String messageText = new JsonResponseParser().parseRegisterResponse(response, MESSAGE_TEXT_KEY);
        Assert.assertTrue("messageText expected to be OK", messageText.equals("OK"));
    }

    @Test
    public void testConfigurationRequest() throws Exception {
        String response = new JsonRequestSender().sendConfigurationRequest(TERMINAL_ID_KEY);
        String messageText = new JsonResponseParser().parseConfigurationResponse(response, MESSAGE_TEXT_KEY);
        String value = new JsonResponseParser().parseConfigurationResponse(response, VALUE_KEY);
        Assert.assertTrue("messageText expected to be OK", messageText.equals("OK"));
        Assert.assertTrue("stateCode value expected to be OK", value.equals("OK"));
    }
}
