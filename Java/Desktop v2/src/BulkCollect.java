import java.util.Date;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.LinkedHashMap;
//import java.util.Date;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class BulkCollect {
	private static String ID, START_DATE, END_DATE;
	
	private static void getParams() {
		Scanner sc = new Scanner(System.in);
		try {
			System.out.println("Enter ID: ");
			ID = sc.nextLine();
			System.out.println("\r\nEnter starting date: ");
			START_DATE = sc.nextLine();
			System.out.println("\r\nEnter end date: ");
			END_DATE = sc.nextLine();
			System.out.println("\r\nID: "+ ID + "\r\nstarting date: " + START_DATE + "\r\nend date: " + END_DATE + "\r\n");
			sc.close();
		}
		catch(Exception e) {
			System.out.println("getParams error occured! Sad");
		}
	}
	
	private static HttpURLConnection setupConnection(URL url, String Method, byte[] dataBytes) throws IOException {
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		conn.setRequestMethod(Method);
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", String.valueOf(dataBytes.length));
		conn.setDoOutput(true);
		conn.getOutputStream().write(dataBytes);
		return conn;
	}
	
	private static byte[] initializeRequest(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder data = new StringBuilder();
		for(Map.Entry<String, String> param : params.entrySet()) {
			if(data.length() != 0) { 
				data.append('&'); 
			}
			data.append(URLEncoder.encode(param.getKey(), "UTF-8"));
			data.append("=");
			data.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
		}
		return data.toString().getBytes("UTF-8");
	}
	
	private static JSONObject getResponse(HttpURLConnection conn) throws UnsupportedEncodingException, IOException, JSONException {
		Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
		StringBuilder sb = new StringBuilder();
		for(int c; (c = in.read()) >= 0;) {
			sb.append((char)c);
		}
		// RETURN RESPONSE AS JSONObject TYPE
		return new JSONObject(sb.toString());//.toString());
	}
		
	// METHOD TO GET DATA FROM SENSOR
	private static void getSensorData() throws IOException, JSONException {		
		//SET A URL
		URL url = new URL("http://robocode.sk/smartpot/php/getSensorData.php");
		
		// CREATE A MAP 
		Map<String, String> params = new LinkedHashMap<>();
		params.put("id", ID);
		params.put("start", START_DATE);
		params.put("end", END_DATE);
		
		// INITIALIZATION CHECK
		byte[] dataBytes = initializeRequest(params);
		
		// SET UP HTTP URL CONNECTION
		HttpURLConnection conn = setupConnection(url, "GET", dataBytes);
		
		// GETTING RESPONSE
		JSONObject response = getResponse(conn);
		
		if (response.getInt("code") == -1) { System.out.println("Recieved code: " + response.getInt("code") + "\r\nREQUEST NOT SENT SUCCESSFULLY!"); }
		else {
			System.out.println("\r\nREQUEST SENT SUCCESSFULLY! HOORAY");
		}
	}
	
	// GETTERS
	public String getID() {
		return ID;		
	}
	
	public String getStartDate() {
		return START_DATE;		
	}
	
	public String getEndDate() {
		return END_DATE;		
	}
	
	// MAIN SCOPE
	public static void main(String[] args) throws IOException, JSONException {
		getParams();
		getSensorData();
	}
}
