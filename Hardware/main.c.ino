#include <ESP8266HTTPClient.h>
#include <ESP8266Wifi.h>
#include <string.h>

#define SSID "xiaomi"
#define PSWD "12345678"
#define sensorPin 0

int id =1;
float tmp;
float hmd;
char const serverWeb[70]="http://robocode.sk/smartpot/php/saveSensorData.php?";
char dataStr[30];
char strToSend[100];
HTTPClient http;


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

char *strHandling(char *serverWeb, char* dataStr, char* strToSend){
  memset(dataStr,0,strlen(dataStr));
  memset(strToSend,0,strlen(strToSend));
  tmp=random(1,10);
  sprintf(dataStr,"id=%d&temp=%lf",id,tmp);
  strcat(strToSend,serverWeb);
  strcat(strToSend,dataStr);
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
