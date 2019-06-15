package json;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static json.JsonConstants.MESSAGE_TEXT_KEY;
import static json.JsonConstants.NUMBER_OF_PICK_CONTAINER;
import static json.JsonConstants.PICK_WALK_ALREADY_ASSIGNED_TO_WORKER;
import static json.JsonConstants.PICK_WALK_ID;
import static json.JsonConstants.SPLIT_ON_FULL;
import static json.JsonConstants.STATE_CODE_KEY;
import static json.JsonConstants.VALUE_KEY;

public class JsonResponseParser {

    private static Logger logger = Logger.getLogger(JsonResponseParser.class);

    public static Object parseRegisterResponse(String response, String key) throws Exception {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
            Object object = null;
            switch (key) {
                case MESSAGE_TEXT_KEY : object = jsonObject.get(MESSAGE_TEXT_KEY);
                if(object != null) return (String) object;
                default: return null;
            }
        } catch (Exception e) {
            logger.error("parseRegisterResponse", e);
            throw e;
        }
    }

    public static Object parseConfigurationResponse(String response, String key) throws Exception {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
            Object object = null;
            switch (key) {
                case MESSAGE_TEXT_KEY : object = jsonObject.get(MESSAGE_TEXT_KEY);
                    if(object != null) return (String) object;
                case VALUE_KEY : JSONObject stateCode = (JSONObject) jsonObject.get(STATE_CODE_KEY);
                    if(stateCode != null) return (String) stateCode.get(VALUE_KEY);
                default: return null;
            }
        } catch (Exception e) {
            logger.error("parseConfigurationResponse", e);
            throw e;
        }
    }

    public static Object parsePickWalkResponse(String response, String key) throws Exception {
        try {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
            Object object = null;
            switch (key) {
                case MESSAGE_TEXT_KEY :
                    object = jsonObject.get(MESSAGE_TEXT_KEY);
                    if(object != null) return object.toString();
                case VALUE_KEY :
                    JSONObject stateCode = (JSONObject) jsonObject.get(STATE_CODE_KEY);
                    String value = stateCode.get(VALUE_KEY).toString();
                    return value;
                case PICK_WALK_ID :
                    object = jsonObject.get(PICK_WALK_ID);
                    if(object != null) return (String) object;
                case PICK_WALK_ALREADY_ASSIGNED_TO_WORKER :
                    object = jsonObject.get(PICK_WALK_ALREADY_ASSIGNED_TO_WORKER);
                    if(object != null) return (boolean) object;
                case NUMBER_OF_PICK_CONTAINER :
                    object = jsonObject.get(NUMBER_OF_PICK_CONTAINER);
                    if(object != null) return (int) object;
                case SPLIT_ON_FULL :
                    object = jsonObject.get(SPLIT_ON_FULL);
                    if(object != null) return (boolean) object;
                default: return null;
            }
        } catch (Exception e) {
            logger.error("parsePickWalkResponse", e);
            throw e;
        }
    }
}
