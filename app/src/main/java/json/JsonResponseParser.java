package json;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static json.JsonConstants.MESSAGE_TEXT_KEY;
import static json.JsonConstants.STATE_CODE_KEY;
import static json.JsonConstants.VALUE_KEY;

public class JsonResponseParser {

    private static Logger logger = Logger.getLogger(JsonResponseParser.class);
    public static String parseRegisterResponse(String response, String key) throws Exception {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
            Object object = null;
            switch (key) {
                case MESSAGE_TEXT_KEY : object = jsonObject.get(MESSAGE_TEXT_KEY);
                if(object != null) return object.toString();
                default: return null;
            }
        } catch (Exception e) {
            logger.error("parseRegisterResponse", e);
            throw e;
        }
    }

    public static String parseConfigurationResponse(String response, String key) throws Exception {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
            Object object = null;
            switch (key) {
                case MESSAGE_TEXT_KEY : object = jsonObject.get(MESSAGE_TEXT_KEY);
                    if(object != null) return object.toString();
                case VALUE_KEY : JSONObject stateCode = (JSONObject) jsonObject.get(STATE_CODE_KEY);
                    String value = stateCode.get(VALUE_KEY).toString();
                    return value;
                default: return null;
            }
        } catch (Exception e) {
            logger.error("parseConfigurationResponse", e);
            throw e;
        }
    }
}
