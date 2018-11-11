import java.util.Scanner;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.Date;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

public class WifiConnectivity {
	// VARIABLES FOR SSID AND PASSWORD
	public static String SSID, PW; 
	// METHOD USED TO GET NETWORK CREDENTIALS
	public static void getCredentials(){
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Enter SSID: ");
			SSID = sc.nextLine();
			System.out.println("\r\nEnter password: ");
			PW = sc.nextLine();
			System.out.println("\r\nSSID: "+ SSID + "\r\nPW: " + PW);
			sc.close();
		}
		catch(Exception e) {
			System.out.println("getCredentials error occured! Sad");
		}
	}
	// METHOD USED TO SEND REQUEST AND HANDLE RESPONSE
	public static void SendRequestAndGetResponse() {
		try {
			URL url = new URL("http://robocode.sk/smartpot/php/transefWifiCredentials.php");
			Map<String, String> params = new LinkedHashMap<>();
			params.put("ssid", SSID);
			params.put("pass", PW);
			
			//INITIALIZATION CHECK		    
		    System.out.println("\nSending 'POST' request to URL : " + url);
			
			StringBuilder postData = new StringBuilder();
			for(Map.Entry<String, String> param : params.entrySet()) {
				if(postData.length() != 0) { postData.append('&'); }
				postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
				postData.append("=");
				postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
			}
			byte[] postDataBytes = postData.toString().getBytes("UTF-8");
			
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
			conn.setDoOutput(true);
			conn.getOutputStream().write(postDataBytes);
			
			
			//HANDLING RESPONSE FROM THE URL
			Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
			
			StringBuilder sb = new StringBuilder();
			for(int c; (c = in.read()) >= 0;) {
				sb.append((char)c);
			}
			
			String response = sb.toString();
			//System.out.println(response); //response in JSON format
			
			JSONObject myResponse = new JSONObject(response.toString());
			if (myResponse.getInt("code") == 1) { System.out.println("Recieved code: " + myResponse.getInt("code") + "\r\nREQUEST SENT SUCCSESSFULLY!"); }
			else if (myResponse.getInt("code") == -1) { System.out.println("Recieved code: " + myResponse.getInt("code") + "\r\nREQUEST NOT SENT SUCCESSFULLY!"); }

		}
		catch(Exception e) {
			System.out.println("SendRequestAndGetResponse error occured! Sad");
			e.printStackTrace();
		}
	}
	
	public static void parseJSON() {
		System.out.println("Parsing JSON response now..\r\n");
		JSONObject jsonText;
		try {
			jsonText = new JSONObject(
					"{\r\n" +
					"  \"amount\":\"5\",\r\n" + 
					"  \"data\": [\r\n" + 
					"      { \"date\":\"2018-10-26 19:53:16\", \"temp\":21.42 , \"hum\":45.47},\r\n" + 
					"      { \"date\":\"2018-10-26 22:12:14\", \"temp\":20.23 , \"hum\":44.01},\r\n" + 
					"      { \"date\":\"2018-10-27 03:18:33\", \"temp\":22.57 , \"hum\":42.19},\r\n" + 
					"      { \"date\":\"2018-10-27 10:12:14\", \"temp\":22.44 , \"hum\":40.12},\r\n" + 
					"      { \"date\":\"2018-10-27 14:29:37\", \"temp\":22.56 , \"hum\":39.01}\r\n" + 
					"  ]\r\n" + 
					"} ");
			int amount =  jsonText.getInt("amount");
			JSONArray data = jsonText.getJSONArray("data");
			//Map<String, Udaje> mapa = new LinkedHashMap();
			//Map<String, String> mapa = new LinkedHashMap();
			for(int i=0; i<amount; i++) {			
				JSONObject tmp = data.getJSONObject(i);
			    String date = tmp.getString("date");
			    Double temp = tmp.getDouble("temp");
			    Double hum = tmp.getDouble("hum");
			    System.out.println("Udaje c." + (i+1) + ": " + date + " " + temp + " " + hum);
			}
		} catch (JSONException e) {
			System.out.println("JSONObject error occured! Sad");
			e.printStackTrace();
		}
		
	}
	
	// MAIN METHOD
	public static void main(String[] args) {
		
		//get SSID and PASSWORD
		WifiConnectivity.getCredentials();
		//SEND REQUEST AND HANDLE RESPONSE
		WifiConnectivity.SendRequestAndGetResponse();
		System.out.println("\r\n");
		//PARSE JSON TEXT PLS
		parseJSON();
				
	}
	
	/*
	class Udaje{
	    private String key;
	    private Date date;
	    private double temp;
	    private double hum;

	    public void Demo(String key, Date date, double temp, double hum){
	        this.key=key;
	        this.date = date;
	        this.temp = temp;
	        this.hum = hum;
	    }
	    
	    public Date getTime() {
	        return date;
	    }
	    public double getTemp() {
	        return temp;
	    }
	    public double getHum() {
	        return hum;
	    }
	    public String getKey() {
	        return key;
	    }
	    @Override
	    public String toString() {
	        return "Data [date=" + date + ", temperature=" + temp + ", humidity=" + hum + "]";
	    }

	}
	*/
	
}
