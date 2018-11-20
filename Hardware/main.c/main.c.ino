/*************************************************************LIBRARIES REQUIRED*************************************************************/

#include <ESP8266HTTPClient.h>
#include <ArduinoJson.h>
#include <ESP8266Wifi.h>
#include <string.h>
#include <ESP8266WebServer.h>
#include <Crypto.h>

/**************************************************************DEFINITIONS PART**************************************************************/
/*******************************************Change these in correspondence to your implementation.*******************************************/

#define SSID "xiaomi"                                                                   /*YOUR WIFI SSID*/
#define PSWD "12345678"                                                                 /*YOUR WIFI PASSWORD*/
#define tempPin 0                                                                       /*Pin where the temp sensor is connected to*/ 
#define ID 1                                                                            /*ID No. of the board*/
String const saveSensorDataURL="http://robocode.sk/smartpot/php/saveSensorData.php?";   /*The Adress of server you want to send the data to*/
String const saltAdress="http://robocode.sk/smartpot/php/getSecurityToken.php";         /*The Adress to get the security key from*/

/**************************************************************GLOBAL VARIABLES***************************************************************/
/**********************************These are defined to lessen the number of parameters passed to functions.**********************************/
/**********************************Very likely to be replaced in more elegant fashion in the future updates.**********************************/

float tmp;                                                                               /*Temperature measurements go here*/
float hum;                                                                               /*Humidity measurements go here*/
HTTPClient http;
DynamicJsonBuffer jsonBuffer(200);
/***************************************************************THE MAIN CODE*****************************************************************/

void setup() { 
  Serial.begin(115200);                                                                  /*Initializes serial connection - for debug purposes*/
  WiFi.begin(SSID, PSWD);                                                                /*Initializes WiFi connection*/
}


float readVals(){                                                                        /*Function handling the reading of temperature(and humidity? ) from the sensors*/
  int reading=analogRead(tempPin);
  float voltage = reading * 3.3; 
  voltage /= 1024.0; 
  tmp = (voltage - 0.5) * 100;
}

String createString(String strToSend, float tmp, float hmd){                             /*Function creating the string which is to be sent by httpGetRequest*/
  String valString="id="+String(ID)+"&tmp="+String(tmp)+"&hum="+String(hmd);
  strToSend=strToSend+saveSensorDataURL+valString;
  return strToSend;
  
}

String getSecurityKey(){
  http.begin(saltAdress);
  http.addHeader("Content-Type", "application/x-www-form-urlencoded");
  http.POST("id=1");
  String payload=http.getString();
  http.end();
  JsonObject& root = jsonBuffer.parseObject(payload);
  String salt=root["salt"];
  Serial.println(salt);
  return salt;
}

String hashSalt(String salt){
  SHA256 hasher;
  
  String key="test_hardcoded_key";
  String mergedStrings=key+salt;                                                          /*Used only to merge key+salt for upcoming conversion to C-type string,*/
  char *toHash=(char*) malloc (mergedStrings.length()+1);
  mergedStrings.toCharArray(toHash,(mergedStrings.length()+1));                                /*because hash function supports only C-type string*/
  
  hasher.doUpdate(toHash,strlen(toHash));
  byte hash[SHA256_SIZE];
  hasher.doFinal(hash);
  free(toHash);
  String hashedKey;
  
  for(byte i;i<SHA256_SIZE;i++){
    Serial.print(hash[i], HEX);
     // Serial.print(hash[i], HEX);
      }
  //Serial.println(hashedKey);
  return hashedKey; 
}


void httpGetRequest(float tmp, float hmd){                                              /*Function sending http get request to a server*/
  String strToSend;
  strToSend=createString(strToSend,tmp,hmd);  
  
  Serial.println(strToSend);

  http.begin(strToSend);
  int httpCode=http.GET();
  http.end();
}
 
void loop() {
  while (WiFi.status()!=WL_CONNECTED){                                                  /*Connecting to the WiFi network and checking connection status*/
    Serial.println("connecting");
    delay(500);
  }
  //httpGetRequest(tmp,hum);
  String salt=getSecurityKey();
  String hashedKey=hashSalt(salt);
  //Serial.println(hashedKey);
  delay(5000);
  
}
