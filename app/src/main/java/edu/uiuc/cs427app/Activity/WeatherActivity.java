package edu.uiuc.cs427app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.util.ThemeUtil;

public class WeatherActivity extends AppCompatActivity {

    JSONObject data = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set the layout and theme
        setTheme(ThemeUtil.getInstance().getPreferredTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // Display username along with app name
        String username = getIntent().getStringExtra("username").toString();
        String appName = getResources().getString(R.string.app_name);
        setTitle(appName + " - " + username);

        // When back button is pressed, go back to DetailsActivity
        Button backButton = (Button) findViewById(R.id.buttonBack);
        backButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });

        // Set the text boxes that display the city and current time
        TextView cityText = findViewById(R.id.textCity);
        TextView timeText = findViewById(R.id.textTime);
        cityText.setText(getIntent().getStringExtra("CITY_NAME"));
        timeText.setText(Calendar.getInstance().getTime().toString());

        // Get the latitude and longitude
        float lat = getIntent().getFloatExtra("LAT", 0);
        float lon = getIntent().getFloatExtra("LONG", 0);
        String key = "7bf63bf0d46336e4fa0cc7d4b63addee";

        // Start the API call
        getJSON(lat, lon, key);

    }

    public void getJSON(final float lat, final float lon, final String key) {
        // Start a new thread so the app can continue while waiting for the API response
        new Thread (new Runnable() {
            @Override
            public void run() {
                try {
                    // Connect to the OpenWeatherMap API
                    URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=" + lat + "&lon=" + lon + "&appid=" + key + "&units=imperial");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                    // Read the response stream into a StringBuffer
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuffer json = new StringBuffer(1024);
                    String tmp = "";

                    while ((tmp = reader.readLine()) != null) {
                        json.append(tmp).append("\n");
                    }

                    reader.close();

                    // Convert the StringBuffer to a JSON object
                    data = new JSONObject((json.toString()));

                    if (data.getInt("cod") != 200) {
                        Log.e("Weather Connection", "Cancelled");
                    }
                } catch (Exception e) {
                    Log.e("Weather Connection", e.getMessage());
                }
                // Use the JSON to update the screen
                updateUI();
            }

            private void updateUI() {
                // Find the text boxes for temperature, weather, humidity, and wind
                TextView tempText = findViewById(R.id.textTemperature);
                TextView weatherText = findViewById(R.id.textWeather);
                TextView humidityText = findViewById(R.id.textHumidity);
                TextView windText = findViewById(R.id.textWind);

                // Set the text of each box using the corresponding JSON values
                try {
                   tempText.setText(data.getJSONObject("main").getString("temp") + " F");
                } catch (Exception e) {
                    Log.e("UI UPDATE", e.getMessage());
                    tempText.setText("Failed to load");
                }

                try {
                    weatherText.setText(data.getJSONArray("weather").getJSONObject(0).getString("description"));
                } catch (Exception e) {
                    Log.e("UI UPDATE", e.getMessage());
                    weatherText.setText("Failed to load");
                }

                try {
                    humidityText.setText(data.getJSONObject("main").getString("humidity") + " %");
                } catch (Exception e) {
                    Log.e("UI UPDATE", e.getMessage());
                    humidityText.setText("Failed to load");
                }

                try {
                    windText.setText(data.getJSONObject("wind").getString("speed") + " mi/hr");
                } catch (Exception e) {
                    Log.e("UI UPDATE", e.getMessage());
                    windText.setText("Failed to load");
                }
            }
        }).start();
    }
}
