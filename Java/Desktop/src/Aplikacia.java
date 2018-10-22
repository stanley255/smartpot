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


public class Aplikacia extends JFrame {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		
		
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
		
		
		JFrame okno = new JFrame("SmartPot");
		
		
		JTextArea txaText = new JTextArea();
		txaText.append(result.toString());
		
		okno.add(txaText);
		okno.setSize(1000, 300);
		okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		okno.setVisible(true);
		
		request.releaseConnection();
	}

}
