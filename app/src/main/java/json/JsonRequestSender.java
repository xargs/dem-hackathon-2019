package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import http.HttpConnectionFactory;

public class JsonRequestSender {

    public String sendRegisterRequest(String terminalId) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getRegisterHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildRegisterRequest(terminalId);
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendConfigurationRequest(String terminalId) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getConfigurationHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildConfigurationRequest(terminalId);
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendPickWalkRequest(String terminalId) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getPickWalkRequestHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildPickWalkRequest(terminalId);
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    private void sendRequest(HttpURLConnection httpURLConnection, String request) throws Exception {
        try{
            OutputStream os = httpURLConnection.getOutputStream();
            byte[] input = request.getBytes("utf-8");
            os.write(input, 0, input.length);
        } catch(IOException e) {
            throw e;
        }
    }

    private String getResponse(HttpURLConnection httpURLConnection) throws Exception {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(httpURLConnection.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }
        return response.toString();
    }
}
