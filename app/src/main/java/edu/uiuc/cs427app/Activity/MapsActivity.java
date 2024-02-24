package edu.uiuc.cs427app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import java.util.List;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.databinding.ActivityMapsBinding;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.util.ThemeUtil;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private CityModel theCity;
    private LatLng cityCoordinates;
    private String cityName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtil.getInstance().getPreferredTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Get the city from the intent
        String cityJson = getIntent().getStringExtra("city");
        Gson gson = new Gson();
        theCity = gson.fromJson(cityJson, CityModel.class);

        // setting cityName & cityCoordinates
        if (theCity != null) {
            cityName = theCity.toString();
            cityCoordinates = new LatLng(theCity.getLatitude(), theCity.getLongitude());
        }
        // updating activity title with city name.
        setTitle(cityName != null ? cityName : "Map View");


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
        mapFragment.getMapAsync(this);


        // updating latitudeTextView and longitudeTextView with cityCoordinates
        TextView latitudeTextView = findViewById(R.id.latitude);
        TextView longitudeTextView = findViewById(R.id.longitude);

        if (cityCoordinates != null) {
            latitudeTextView.setText(String.format("Latitude: %.6f", cityCoordinates.latitude));
            longitudeTextView.setText(String.format("Longitude: %.6f", cityCoordinates.longitude));
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // zooming and adding marker on city coordinates.
        if ((cityCoordinates != null) && (cityName != null)) {
            mMap.addMarker(new MarkerOptions().position(cityCoordinates).title(String.format("Marker in %s", cityName)));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(cityCoordinates, 14));
        }
    }
}