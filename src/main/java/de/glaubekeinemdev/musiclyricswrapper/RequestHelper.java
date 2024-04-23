package de.glaubekeinemdev.musiclyricswrapper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class RequestHelper {

    public HttpURLConnection establishConnection(final URL url, final boolean setRequestProperties) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            if (setRequestProperties)
                setRequestProperties(urlConnection);

            return urlConnection;
        } catch (IOException e) {
            return null;
        }
    }

    private void setRequestProperties(final HttpURLConnection urlConnection) {
        urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/75.0.3770.100 Safari/537.36");
        urlConnection.setRequestProperty("accept-language", "de-DE");
        urlConnection.setRequestProperty("Content-Type", "application/json");
        urlConnection.setRequestProperty("Accept", "application/json");
    }

    public String getRequest(final HttpURLConnection source) {
        try {
            source.setRequestMethod("GET");

            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(source.getInputStream(), StandardCharsets.UTF_8));

            String line;
            final StringBuilder output = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null)
                output.append(line);

            bufferedReader.close();

            return output.toString();
        } catch (Exception e) {
            return null;
        }
    }

}
