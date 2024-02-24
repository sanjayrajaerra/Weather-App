package edu.uiuc.cs427app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.services.CityService;
import edu.uiuc.cs427app.services.UserService;
import edu.uiuc.cs427app.util.ThemeUtil;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener{

    CityModel theSelectedCity;

    /**
     * Invoked on activity creation to initialize components
     * @param savedInstanceState
     */

    private CityService cityService;
    private CityModel city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtil.getInstance().getPreferredTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Display username along with app name
        String username = getIntent().getStringExtra("username").toString();
        String appName = getResources().getString(R.string.app_name);
        setTitle(appName + " - " + username);

        // Process the Intent payload that has opened this Activity and show the information accordingly
        String cityJson = getIntent().getStringExtra("city");
        Gson gson = new Gson();
        CityModel city = gson.fromJson(cityJson, CityModel.class);
        theSelectedCity = city;
        String welcome = "Welcome to " + city.getName();
        String cityWeatherInfo = "Detailed information about the weather of "+ city.getName();

        // Initializing the GUI elements
        TextView welcomeMessage = findViewById(R.id.welcomeText);
        TextView cityInfoMessage = findViewById(R.id.cityInfo);

        welcomeMessage.setText(welcome);
        cityInfoMessage.setText(cityWeatherInfo);
        // Get the weather information from a Service that connects to a weather server and show the results

        // Set up listeners for the map and weather buttons
        Button buttonMap = findViewById(R.id.mapButton);
        buttonMap.setOnClickListener(this);
        Button buttonWeather = findViewById((R.id.weatherButton));
        buttonWeather.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.mapButton:
                // Launch the map activity, pass in the cityModel
                intent = new Intent(this, MapsActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(theSelectedCity);
                intent.putExtra("city", json);
                startActivity(intent);
                break;
            case R.id.weatherButton:
                // Launch the weather activity, pass in the name, latitude, and longitude of the city
                intent = new Intent(this, WeatherActivity.class);
                intent.putExtra("username", getIntent().getStringExtra("username"));
                intent.putExtra("CITY_NAME", theSelectedCity.getName());
                intent.putExtra("LAT", theSelectedCity.getLatitude());
                intent.putExtra("LONG", theSelectedCity.getLongitude());
                startActivity(intent);
                break;
        }
    }
}

