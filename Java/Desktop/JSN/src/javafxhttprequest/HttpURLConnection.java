
package javafxhttprequest;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

;

public class HttpURLConnection {
	private StringBuffer result;
	private int code;
	private double temp;
	private double hum;
	
	public HttpURLConnection() throws Exception {
		sendGet();
	}
	

	// HTTP GET request
	public void sendGet() throws Exception {
		String url = "http://robocode.sk/smartpot/php/getLatestSensorData.php?id=1";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		
		//parsovanie JSON
		JSONObject jsnObject = new JSONObject(result.toString());
		code= jsnObject.getInt("code");
		temp = jsnObject.getDouble("temp");
		hum = jsnObject.getDouble("hum");
		
	}
	
	public  String getResult() {
		return result.toString();
	}
	
	public int getCode() {
		return code;
	}
	
	public double getTemp() {
		return temp;
	}
	
	public double getHum() {
		return hum;
	}

}
