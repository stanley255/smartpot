
package javafxhttprequest;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class JavaFXHTTPRequest extends Application {
	private int code;
	private double temp;
	private double hum;
    private HttpURLConnection http;
	
    @Override
    public void start(Stage stage) throws Exception {
    	 
    	        stage.setTitle("Smartpot");
    	        
    	        Text txtCode = new Text ();
    	        Text txtTemp = new Text();
    	        Text txtHum = new Text();
    	        
    	        Button btn = new Button();
    	        btn.setText("Zisti aktualne hodnoty");
    	        btn.setOnAction(new EventHandler<ActionEvent>() {
    	 
    	            @Override
    	            public void handle(ActionEvent event) {
    	            	
    	            	
						try {
							http = new HttpURLConnection();
							
							code = http.getCode();
							String SCode = "Code: " + Integer.toString(code);
							txtCode.setText(SCode);
							
							temp = http.getTemp();
							String STemp = "Temperature: " + Double.toString(temp);
							txtTemp.setText(STemp);
							
							hum = http.getHum();
							String SHum = "Humidity: " + Double.toString(hum);
							txtHum.setText(SHum);
							
						} catch (Exception e) {
							e.printStackTrace();
						}
    	            }
    	        });
    	        
    	        VBox pane = new VBox();
    	        pane.getChildren().addAll(btn,txtCode, txtTemp, txtHum);
    	        
    	        stage.setScene(new Scene(pane, 300, 150));
    	        stage.show();
    	    }
    	
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
