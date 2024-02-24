package edu.uiuc.cs427app.Activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.services.CityService;
import edu.uiuc.cs427app.services.UserService;
import edu.uiuc.cs427app.util.ThemeUtil;


public class AddCityActivity extends AppCompatActivity implements View.OnClickListener{
    // global variables for access in lambdas
    String city;
    List<CityModel> citiesFromUser;

    private UserService userService;

    private CityService cityService;

    private String currentUser;

    /**
     * Invoked on activity creation to initialize components
     * @param savedInstanceState
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Set the theme based on user's preference
        setTheme(ThemeUtil.getInstance().getPreferredTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_city);

        // Get the current user and city services
        userService = UserService.getInstance();
        cityService = CityService.getInstance();

        // Get User From Intent
        currentUser = getIntent().getStringExtra("username").toString();

        // Display username along with app name
        String appName = getResources().getString(R.string.app_name);
        setTitle(appName + " - " + currentUser);

        // Get the active user profile
        UserModel user = userService.getUserByUsername(currentUser);

        // Get the list of all city models and create a list of names for searching
        List<CityModel> citiesFromDb = cityService.getAllCities();
        List<String> citiesList = new ArrayList<>();
        for (int i = 0; i < citiesFromDb.size(); i++) {
            citiesList.add(citiesFromDb.get(i).getName());
        }

        // Get the user's list of cities
        citiesFromUser = userService.getCitiesForUser(user);

        // Initialize GUI elements
        Button addButton = (Button) findViewById(R.id.buttonAdd);
        Button removeButton = (Button) findViewById(R.id.buttonRemove);
        Button backButton = (Button) findViewById(R.id.buttonBack);

        // Autocomplete functionality using full city list
        AutoCompleteTextView inputCity = (AutoCompleteTextView) findViewById(R.id.inputCity);
        ArrayAdapter<String> city_adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, citiesList);
        inputCity.setAdapter(city_adapter);
        inputCity.setThreshold(1);

        // when the user selects the view display the drop down of available cities to select from
        inputCity.setOnTouchListener(new View.OnTouchListener(){
            // define the onTouch event to take place when the user selects the view component
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                inputCity.showDropDown();
                return false;
            }
        });


        // Initialize city variable to text box contents
        city = inputCity.getText().toString();

        // Watch for updates to inputCity text box
        inputCity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputCity.showDropDown();
            }
            @Override
            // Update every time a character is added or deleted
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // set city to text box contents
                city = inputCity.getText().toString().trim();

                // if the input is a valid city and is in the user's list, enable the remove button
                // if the input is a valid city and is not in the user's list, enable the add button
                // otherwise disable both buttons
                if (citiesList.contains(city)) {
                    CityModel newCity = citiesFromDb.get(citiesList.indexOf(city));
                    if (citiesFromUser.contains(newCity)) {
                        removeButton.setClickable(true);
                        removeButton.setAlpha(1f);
                        addButton.setClickable(false);
                        addButton.setAlpha(0.4f);
                    } else {
                        addButton.setClickable(true);
                        addButton.setAlpha(1f);
                        removeButton.setClickable(false);
                        removeButton.setAlpha(0.4f);
                    }
                } else {
                    removeButton.setClickable(false);
                    removeButton.setAlpha(0.4f);
                    addButton.setClickable(false);
                    addButton.setAlpha(0.4f);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        // When the add button is pressed, convert city string to CityModel and add to user's list
        addButton.setOnClickListener(view -> {
            int idx = citiesList.indexOf(city);
            if (idx == -1) return;
            CityModel newCity = citiesFromDb.get(idx);
            userService.addCityForUser(user, newCity);
            citiesFromUser = userService.getCitiesForUser(user);
            removeButton.setClickable(true);
            removeButton.setAlpha(1f);
            addButton.setClickable(false);
            addButton.setAlpha(0.4f);
        });

        // When the remove button is pressed, convert city string to CityModel and remove from user's list
        removeButton.setOnClickListener(view -> {
            int idx = citiesList.indexOf(city);
            if (idx == -1) return;
            CityModel removeCity = citiesFromDb.get(idx);
            if (citiesFromUser.contains(removeCity)) {
                userService.removeCity(user, removeCity);
                citiesFromUser = userService.getCitiesForUser(user);
                addButton.setClickable(true);
                addButton.setAlpha(1f);
                removeButton.setClickable(false);
                removeButton.setAlpha(0.4f);
            }
        });

        // When the back button is pressed, go to main activity
        backButton.setOnClickListener(view -> {
            Intent resultIntent = new Intent();
            setResult(Activity.RESULT_OK, resultIntent);
            finish();
        });


    }

    @Override
    public void onClick(View view) {
    }
}

