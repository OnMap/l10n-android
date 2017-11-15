package onmap.co.il.remote.localization.network;

import android.support.annotation.NonNull;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;


public class ApiConnection {
    private Timeout timeout;

    public ApiConnection(Timeout timeout) {
        this.timeout = timeout;
    }

    public String get(String httpUrl) throws IOException {
        URL mUrl = new URL(httpUrl);
        URLConnection urlConnection = mUrl.openConnection();
        HttpURLConnection httpConnection = (HttpURLConnection) urlConnection;
        httpConnection.setRequestMethod("GET");
        httpConnection.setUseCaches(false);
        httpConnection.setAllowUserInteraction(false);
        httpConnection.setConnectTimeout(timeout.getConnectionTimeout());
        httpConnection.setReadTimeout(timeout.getReadTimeout());
        try {
            httpConnection.connect();
        } catch (Exception e) {
            throw new IOException(e.getMessage());
        }


        int responseCode = httpConnection.getResponseCode();
        String body = getBody(httpConnection);
        if (responseCode == HttpURLConnection.HTTP_OK) {
            return body;
        } else {
            Log.w("Http response", body);
            throw new IOException("Response code " + responseCode);
        }

    }

    @NonNull
    private String getBody(HttpURLConnection httpConnection) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        return sb.toString();
    }

    public static class Timeout {
        private int connectionTimeout;
        private int readTimeout;

        public Timeout(int connectionTimeout, int readTimeout) {

            this.connectionTimeout = connectionTimeout;
            this.readTimeout = readTimeout;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public int getReadTimeout() {
            return readTimeout;
        }

        public void setReadTimeout(int readTimeout) {
            this.readTimeout = readTimeout;
        }
    }
}
