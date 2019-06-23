package Utilities;

import GUI.SmartpotFrame;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class BulkDataCollector {
    private final SmartpotFrame frame;
    private String id;
    private String start;
    private String end;

    public BulkDataCollector(SmartpotFrame frame) {
        this.frame = frame;
        id = "";
        start = "";
        end = "";
    }

    public String magic() {
        try {
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("id", "1");
            params.put("start", "2018-10-26 22:12:14");
            params.put("end", "2018-10-27 10:12:14");
            InputStream response = createConnection(initializeData(params));
            return handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private String handleResponse(InputStream response) throws IOException {
        if(response == null) {
            throw new IllegalArgumentException();
        }
        return getResponseAsString(response);
    }

    private String getResponseAsString(InputStream response) throws IOException {
        Reader in = new BufferedReader(new InputStreamReader(response, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for(int c; (c = in.read()) >= 0;) {
            sb.append((char)c);
        }
        System.out.println(sb.toString());
        return sb.toString();
    }

    private InputStream createConnection(String postDataBytes) throws IOException {
        URL url = new URL("http://robocode.sk/smartpot/php/getSensorData.php" + postDataBytes);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);
        if(conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return conn.getInputStream();
        }
        return null;
    }

    private String initializeData(LinkedHashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder queryParameters = new StringBuilder();
        queryParameters.append('?');
        for(Map.Entry<String, String> param : params.entrySet()) {
            if(queryParameters.length() != 0) { queryParameters.append('&'); }
            queryParameters.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            queryParameters.append("=");
            queryParameters.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        System.out.println(queryParameters.toString());
        return queryParameters.toString();
    }
}
