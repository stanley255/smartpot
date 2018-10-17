/*LIBRARIES REQUIRED*/
#include <ESP8266HTTPClient.h>
#include <ESP8266Wifi.h>
#include <string.h>
/*DEFINITIONS PART*/
#define SSID "xiaomi"                                                                    /*change this to your WiFi SSID*/
#define PSWD "12345678"                                                                  /*change this to your WiFi password*/
#define sensorPin 0
#define ID 1
/*HTTP PART*/
HTTPClient http;

/*
 * Global Variables - these are defined to lessen 
 * the number of parameters passed to functions
*/
char const saveSensorDataURL[100]="http://robocode.sk/smartpot/php/saveSensorData.php?";       /*The Adress of server you want to send the data to*/
float tmp;                                                                               /*Temperature measurements go here*/
float hmd;                                                                               /*Humidity measurements go here*/


void setup() { 
  Serial.begin(115200);                                                                  /*Initializes serial connection - for debug purposes*/
  WiFi.begin(SSID, PSWD);                                                                /*Initializes WiFi connection*/
}


float readVals(){                                                                        /*Function handling the reading of temperature(and humidity? :) ) from the sensors*/
  int reading=analogRead(sensorPin);
  float voltage = reading * 3.3; 
  voltage /= 1024.0; 
  tmp = (voltage - 0.5) * 100;
}

void createString(char *strToSend, float tmp, float hmd){                               /*Function creating the string which is to be sent by httpGetRequest*/
  char *valString=(char*) malloc (70);
  
  sprintf(valString,"id=%d&temp=%lf&hum=%lf",ID,tmp,hmd);
  strcat(strToSend,saveSensorDataURL);
  strcat(strToSend,valString);  
  free (valString);
}

void httpGetRequest(float tmp, float hmd){                                              /*Function sending http get request to a server*/
  char *strToSend=(char*) malloc (200);
  createString(strToSend,tmp,hmd);  
  
  Serial.println(strToSend);

  http.begin(strToSend);
  int httpCode=http.GET();
  http.end();
  free(strToSend);
}
 
void loop() {
  while (WiFi.status()!=WL_CONNECTED){                                                  /*Connecting to the WiFi network and checking connection status*/
    Serial.println("connecting");
    delay(500);
  }
  httpGetRequest(6.9,6.66);
  delay(2000);
  
}
