package json;

import junit.framework.Assert;

import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class TestJsonMessages {

    private static JsonRequestBuilder jsonRequestBuilder;
    private static URL url;
    private static HttpURLConnection httpURLConnection;

    @BeforeClass
    public static void initAll() throws Exception {
        jsonRequestBuilder = new JsonRequestBuilder();
    }

    public void buildURLParams(String url) throws Exception {
        this.url = new URL(url);
        httpURLConnection = (HttpURLConnection) this.url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
    }

    @Test
    public void testRegisterRequest() throws Exception {
        buildURLParams(jsonRequestBuilder.buildRegisterUrl());
        String request  = jsonRequestBuilder.buildRegisterRequest(JsonConstants.TERMINAL_ID_KEY);
        try(OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = request.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(response.toString());
            String messageText = jsonObject.get(JsonConstants.MESSAGE_TEXT_KEY).toString();
            Assert.assertTrue("messageText expected to be OK", messageText.equals("OK"));
        }
    }

    @Test
    public void testConfigurationRequest() throws Exception {
        buildURLParams(jsonRequestBuilder.buildConfigurationRequestUrl());
        String request  = jsonRequestBuilder.buildConfigurationRequest(JsonConstants.TERMINAL_ID_KEY);
        try(OutputStream os = httpURLConnection.getOutputStream()) {
            byte[] input = request.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(response.toString());
            String messageText = jsonObject.get(JsonConstants.MESSAGE_TEXT_KEY).toString();
            JSONObject stateCode = (JSONObject) jsonObject.get(JsonConstants.STATE_CODE_KEY);
            String value = stateCode.get(JsonConstants.VALUE_KEY).toString();
            Assert.assertTrue("messageText expected to be OK", messageText.equals("OK"));
            Assert.assertTrue("stateCode expected to be OK", value.equals("OK"));
        }
    }
}
