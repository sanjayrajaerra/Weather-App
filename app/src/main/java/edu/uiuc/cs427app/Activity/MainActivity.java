package edu.uiuc.cs427app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import java.util.ArrayList;
import java.util.List;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.databinding.ActivityMainBinding;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.services.CityService;
import edu.uiuc.cs427app.services.UserService;
import edu.uiuc.cs427app.util.AppDb;
import edu.uiuc.cs427app.util.ThemeUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // Add TAG for application logging
    private static final String TAG = MainActivity.class.toString();
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    // The database
    private AppDb db;

    // The user and city services
    static UserService theUserService;
    static CityService theCityService;

    // Add/Remove City Response
    private static final int CITY_REQUEST_RESULT = 1;

    // Update THEME Response
    private static final int THEME_REQUEST_RESULT = 2;

    public String currentUser;

    /**
     * Called when the activity is created the initialize components
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtil.getInstance().getPreferredTheme());

        // Display username along with app name
        currentUser = getIntent().getStringExtra("username").toString();
        System.out.println(currentUser);
        String appName = getResources().getString(R.string.app_name);
        setTitle(appName + " - " + currentUser);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get Service Instance
        theUserService = theUserService.getInstance();
        theCityService = theCityService.getInstance();

        // Initializing the UI components
        // The list of locations should be customized per user (change the implementation so that
        // buttons are added to layout programmatically
        Button buttonNew = findViewById(R.id.buttonAddLocation);
        Button LogOutButton = (Button) findViewById(R.id.LogOutButton);
        Button changeTheme = findViewById(R.id.changeTheme);

        UserModel user = theUserService.getUserByUsername(currentUser);
        List<CityModel> userCities = theUserService.getCitiesForUser(user);
        ListView userCitiesList = findViewById(R.id.userCities);
        ArrayAdapter<CityModel> listAdapter = new ArrayAdapter<CityModel>(this, android.R.layout.simple_list_item_1, userCities);
        userCitiesList.setAdapter(listAdapter);

        // Start details activity when list item is clicked
        userCitiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                Gson gson = new Gson();
                String json = gson.toJson(userCities.get(position));

                intent.putExtra("city", json);
                intent.putExtra("username",currentUser);
                startActivity(intent);
            }
        });

        //Set logout Button listener
        LogOutButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        });

        buttonNew.setOnClickListener(this);
        changeTheme.setOnClickListener(this);
    }

    // Interact with main activity
    // Identify if user clicks occur and if so determine whether a button view was clicked
    // and take appropriate action
    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.buttonAddLocation:
                // Implement this action to add a new location to the list of locations
                intent = new Intent(this, AddCityActivity.class);
                intent.putExtra("username",currentUser);
                startActivityForResult(intent,CITY_REQUEST_RESULT);
                break;
            case R.id.changeTheme:
                // Implement this action to add a new location to the list of locations
                intent = new Intent(this, ThemeActivity.class);
                intent.putExtra("username",currentUser);
                startActivityForResult(intent,THEME_REQUEST_RESULT);
                break;
        }
    }

    //Result of Adding/removing city to refresh list.
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case (CITY_REQUEST_RESULT) : {
                if (resultCode == Activity.RESULT_OK) {
                    recreate();
                }
                break;
            }
            case (THEME_REQUEST_RESULT) : {
                if (resultCode == Activity.RESULT_OK) {
                    recreate();
                }
                break;
            }
        }
    }
}

