<h1>HCL Tech Enviride</h2>
<b>Description</b><br/>
HCL Tech Enviride is a mobile application designed to simplify cycle allottement and returning using a IoT based Android app.


<h5>Features</h5>
<ol type = "1">
<li>Requesting a cycle to allott.<br/></li>
<li>return it after using.</li>
<li>Raise complaints if the cycle returned in damage state</li>
<li>Manage Cycles</li>
  
</ol>
 
 
 <h5>Technologies Used</h5>

 <ol>
   <li>Android Studio</li>
   <li>Kotlin</li>
   <li>Firebase (Firestore, Authentication)</li>
   <li>ZXing Barcode Library</li>
   <li>Embeded C++ </li>
 </ol>




Installation
Clone the repository: git clone https://github.com/khajavali-sk/HCLTechEnviRide.git
Open the project in Android Studio
Build and run the project on an Android device or emulator




<h3>ESP8266 (Arduino) code</h3>

<pre>
<code class="language-cpp">
#include &lt;ESP8266WiFi.h&gt;
#include &lt;Firebase_ESP_Client.h&gt;
#include &lt;addons/TokenHelper.h&gt;
#include &lt;ArduinoJson.h&gt;

#define WIFI_SSID "xyz"
#define WIFI_PASSWORD "--pass-word--"

#define API_KEY "--GPT ADDANKI--"  //not original

#define FIREBASE_PROJECT_ID "hcltech-enviride"

#define USER_EMAIL "XYZ@email.com"  //not original
#define USER_PASSWORD "______"    //not original

#define LED_PIN D4

FirebaseData fbdo;
FirebaseAuth auth;
FirebaseConfig config;

void setup() {
  Serial.begin(9600);

  WiFi.begin(WIFI_SSID, WIFI_PASSWORD);
  Serial.println("Connecting to WiFi...");
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("");
  Serial.println("WiFi connected");
  Serial.println("IP address: ");
  Serial.println(WiFi.localIP());

  config.api_key = API_KEY;
  auth.user.email = USER_EMAIL;
  auth.user.password = USER_PASSWORD;

  Firebase.begin(&config, &auth);
  Serial.println("connected to the data base");

  pinMode(LED_PIN, OUTPUT);  // Set the LED pin as output
}

void loop() {
  String path = "/Cycles/22222";
  if (Firebase.Firestore.getDocument(&fbdo, FIREBASE_PROJECT_ID, "", path.c_str())) {
    String payload = fbdo.payload().c_str();
    Serial.println(fbdo.payload().c_str());
    String value = parseJSON(payload, "allotted");  // Replace "your_key" with your actual key

    Serial.printf("%s", value);

    if (value == "True") {
      digitalWrite(LED_PIN, HIGH);
      Serial.println("on");
    } else if (value == "False") {
      digitalWrite(LED_PIN, LOW);
      Serial.println("off");
    }
  } else {
    Serial.println(fbdo.errorReason());
  }
  delay(5000);
}

String parseJSON(String payload, String key) {
  DynamicJsonDocument doc(1024);
  deserializeJson(doc, payload);
  String value = doc["fields"][key]["stringValue"];
  return value;
}
</code>
</pre>

