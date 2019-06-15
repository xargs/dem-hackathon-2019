package http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    private URL url;
    private HttpURLConnection httpURLConnection;
    private String requestMethod = "POST";
    public static final String REQUEST_METHOD_PUT = "PUT";
    public static final String REQUEST_METHOD_GET = "GET";

    public HttpConnection(String url) throws Exception {
        this.url = new URL(url);
        httpURLConnection = (HttpURLConnection) this.url.openConnection();
        httpURLConnection.setRequestMethod(this.requestMethod);
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }

    public void connect() throws IOException {
        httpURLConnection.connect();
    }

    public void disconnect(){
        httpURLConnection.disconnect();
    }

    public OutputStream getOutputStream() throws IOException{
        return httpURLConnection.getOutputStream();
    }

    public String getResponseMessage() throws IOException{
        return httpURLConnection.getResponseMessage();
    }

    public int getResponseCode() throws IOException{
        return httpURLConnection.getResponseCode();
    }
}
