import java.util.Scanner;
import java.util.Map;
import java.util.LinkedHashMap;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder; 
import org.json.JSONObject;

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
				if(postData.length()!=0) { postData.append('&'); }
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
			
			
			//GETTING RESPONSE FROM THE URL
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

	public static void main(String[] args) {
		
		//get SSID and PASSWORD
		WifiConnectivity.getCredentials();
		//SEND REQUEST AND HANDLE RESPONSE
		WifiConnectivity.SendRequestAndGetResponse();
		
	}
	
}
