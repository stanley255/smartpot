/*LIBRARIES REQUIRED*/
#include <ESP8266HTTPClient.h>
#include <ESP8266Wifi.h>
#include <string.h>
/*SSID PART*/
#define SSID "xiaomi"
#define PSWD "12345678"
#define sensorPin 0
/*HTTP PART*/
HTTPClient http;

char strToSend[200];
char const saveSensorDataURL[100]="http://robocode.sk/smartpot/php/saveSensorData.php?";
char GETPart[100];

int id =1;
float tmp;
float hmd;

void setup() { 
  Serial.begin(115200);
  WiFi.begin(SSID, PSWD);
}

float readTemp(){
  int reading=analogRead(sensorPin);
  float voltage = reading * 3.3; 
  voltage /= 1024.0; 
  float temperature = (voltage - 0.5) * 100;
  return temperature;
}

char *strHandling(char *saveSensorDataURL, char* GETPart, char* strToSend){
  memset(GETPart,0,strlen(GETPart));
  memset(strToSend,0,strlen(strToSend));
  tmp=random(1,10);
  sprintf(GETPart,"id=%d&temp=%lf",id,tmp);
  strcat(strToSend,saveSensorDataURL);
  strcat(strToSend,GETPart);
  Serial.println(strToSend);
  return (strToSend);
}
 
void loop() {
  while (WiFi.status()!=WL_CONNECTED){
    Serial.println("connecting");
    delay(500);
  }

  
  http.begin(strToSend);
  int httpCode=http.GET();
  http.end();
  delay(10000);
  
  
}
