package json.outbound;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.List;

import http.HttpConnection;
import http.HttpConnectionFactory;

public class JsonRequestSender {

    public String sendRegisterRequest() throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getRegisterHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildRegisterRequest();
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendDeregisterRequest() throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getDeregisterHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildDeregisterRequest();
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendConfigurationRequest() throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getConfigurationHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildConfigurationRequest();
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendPickWalkRequest() throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getPickWalkRequestHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildPickWalkRequest();
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendAssignPickContainerRequest(String pickWalkId, String pickContainerId, String unitType, String position) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getAssignPickContainerRequestHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildAssignPickContainerRequest(pickWalkId, pickContainerId, unitType, position);
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendPickRequest(String pickWalkId) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getPickRequestHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildPickRequest(pickWalkId);
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendConfirmPickRequest(String confirmationCode, String primaryKey, int picked) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getConfirmPickRequestHttpConnection().getHttpURLConnection();
        httpURLConnection.setRequestMethod(HttpConnection.REQUEST_METHOD_PUT); // PUT method for confirm pick request
        String request  = JsonRequestBuilder.buildConfirmPickRequest(confirmationCode, primaryKey, picked);
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendPickWalkFinishRequest(String pickWalkId) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getPickWalkFinishRequestHttpConnection().getHttpURLConnection();
        String request  = JsonRequestBuilder.buildPickWalkFinishRequest(pickWalkId);
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendPickContainerConfirmationRequest(String destinationLocationId, List<String> pickContainerIds) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getPickWalkFinishRequestHttpConnection().getHttpURLConnection();
        httpURLConnection.setRequestMethod(HttpConnection.REQUEST_METHOD_PUT); // PUT method for pick container confirm request
        String request  = JsonRequestBuilder.buildPickContainerConfirmationRequest(destinationLocationId, pickContainerIds);
        sendRequest(httpURLConnection, request);
        return getResponse(httpURLConnection);
    }

    public String sendSkuImageRequest(String sku) throws Exception {
        HttpURLConnection httpURLConnection = HttpConnectionFactory.getSkuImageRequestHttpConnection(sku).getHttpURLConnection();
        httpURLConnection.setRequestMethod(HttpConnection.REQUEST_METHOD_GET); // GET method for sku image request
        httpURLConnection.setRequestProperty("Accept", null); // Set accept to null to be able to read image
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
