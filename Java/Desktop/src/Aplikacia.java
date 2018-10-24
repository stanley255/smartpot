import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.JFrame;
import javax.swing.JTextArea;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONException;
import org.json.JSONObject;


public class Aplikacia extends JFrame {

	public static void main(String[] args) throws ClientProtocolException, IOException, JSONException {
		
		
		
		String url = "http://robocode.sk/smartpot/php/getLatestSensorData.php?id=1";

		HttpClient client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);

		// add request header
		//request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);

		System.out.println("Response Code : "   + response.getStatusLine().getStatusCode());

		BufferedReader rd = new BufferedReader(
			new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		System.out.println(result);
		
		JSONObject jsnObject = new JSONObject(result.toString());
		int action = jsnObject.getInt("action");
		double temp = jsnObject.getDouble("temp");
		double hum = jsnObject.getDouble("hum");
		
		System.out.println("action: " + action);
		System.out.printf("temp: %.2f%n" , temp);
		System.out.printf("hum: %.2f%n" , hum);
		
		
		JFrame okno = new JFrame("SmartPot");
		
		
		JTextArea txaText = new JTextArea();
		txaText.append(result.toString() + "\n");
		txaText.append("action: " + action + "\n");
		txaText.append("temp: " + temp + "\n");
		txaText.append("hum: " + hum);
		
		okno.add(txaText);
		okno.setSize(1000, 300);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setVisible(true);
		
		request.releaseConnection();
	}

}
