package http;

import java.net.HttpURLConnection;
import java.net.URL;

public class HttpConnection {

    private URL url;
    private HttpURLConnection httpURLConnection;

    public HttpConnection(String url) throws Exception {
        this.url = new URL(url);
        httpURLConnection = (HttpURLConnection) this.url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setRequestProperty("Content-Type", "application/json; utf-8");
        httpURLConnection.setRequestProperty("Accept", "application/json");
        httpURLConnection.setDoOutput(true);
    }

    public HttpURLConnection getHttpURLConnection() {
        return httpURLConnection;
    }
}
