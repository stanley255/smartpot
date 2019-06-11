package Utilities;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

public class WifiConnection {
    private String ssid;
    private String password;

    public WifiConnection(String ssid, String password) {
        this.ssid = ssid;
        this.password = password;
    }


    public void connect() {
        try {
            LinkedHashMap<String, String> params = new LinkedHashMap<>();
            params.put("ssid", ssid);
            params.put("pass", password);
            InputStream response = createConnection(initializeData(params));
            handleResponse(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleResponse(InputStream response1) throws IOException {
        Reader in = new BufferedReader(new InputStreamReader(response1, StandardCharsets.UTF_8));

        StringBuilder sb = new StringBuilder();
        for(int c; (c = in.read()) >= 0;) {
            sb.append((char)c);
        }

        String response = sb.toString();
//        System.out.println(response); //response in JSON format

        JSONObject myResponse = new JSONObject(response.toString());
        if (myResponse.getInt("code") == 1) { System.out.println("Recieved code: " + myResponse.getInt("code") + "\r\nREQUEST SENT SUCCSESSFULLY!"); }
        else if (myResponse.getInt("code") == -1) { System.out.println("Recieved code: " + myResponse.getInt("code") + "\r\nREQUEST NOT SENT SUCCESSFULLY!"); }
    }

    private InputStream createConnection(byte[] postDataBytes) throws IOException {
        URL url = new URL("http://robocode.sk/smartpot/php/transefWifiCredentials.php");
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);
        return conn.getInputStream();
    }

    private byte[] initializeData(LinkedHashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder postData = new StringBuilder();
        for(Map.Entry<String, String> param : params.entrySet()) {
            if(postData.length() != 0) { postData.append('&'); }
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append("=");
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        return postData.toString().getBytes(StandardCharsets.UTF_8);
    }
}
