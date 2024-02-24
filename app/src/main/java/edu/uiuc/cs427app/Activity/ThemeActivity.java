package edu.uiuc.cs427app.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.services.UserService;
import edu.uiuc.cs427app.util.ThemeUtil;

public class ThemeActivity extends AppCompatActivity implements View.OnClickListener {

    private Button primaryRedButton, primaryOrangeButton, primaryGreenButton, primaryTealButton, primaryBlueButton, primaryPurpleButton;

    private Button secondaryBlackButton, secondaryWhiteButton;
    private Button saveTheme;

    private String currPrimaryColor, currSecondaryColor;

    private String username;

    private UserService userService;

    /**
     * Create Theme Activity. If From Create Account activity, no username is needed. If From Main Activity, username is needed in order to update database.
     * User Can select primary theme color. They can also select secondary (font color). save theme to return to original activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(ThemeUtil.getInstance().getPreferredTheme());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme);
        if(getIntent().getStringExtra("username") != null) {

            // Display username along with app name
            username = getIntent().getStringExtra("username").toString();
            String appName = getResources().getString(R.string.app_name);
            setTitle(appName + " - " + username);

            userService = UserService.getInstance();
            UserModel test;
            if (username != null) {
                test = userService.getUserByUsername(username);
                if (test == null) {
                    System.out.println("user not found");
                } else {
                    System.out.println(test.toString());
                }
            }
        }

        primaryRedButton = findViewById(R.id.primRedButton);
        primaryOrangeButton= findViewById(R.id.primOrangeButton);
        primaryGreenButton = findViewById(R.id.primGreenButton);
        primaryTealButton= findViewById(R.id.primTealButton);
        primaryBlueButton = findViewById(R.id.primBlueButton);
        primaryPurpleButton = findViewById(R.id.primPurpleButton);

        secondaryBlackButton = findViewById(R.id.secBlackButton);
        secondaryWhiteButton = findViewById(R.id.secWhiteButton);

        saveTheme = findViewById(R.id.saveTheme);

        primaryRedButton.setOnClickListener(this);
        primaryOrangeButton.setOnClickListener(this);
        primaryGreenButton.setOnClickListener(this);
        primaryTealButton.setOnClickListener(this);
        primaryBlueButton.setOnClickListener(this);
        primaryPurpleButton.setOnClickListener(this);

        secondaryBlackButton.setOnClickListener(this);
        secondaryWhiteButton.setOnClickListener(this);

        saveTheme.setOnClickListener(this);

        List<String> colors = ThemeUtil.getInstance().getPreferredColors();
        currPrimaryColor = colors.get(0);
        currSecondaryColor = colors.get(1);

        markSelectedPrimary(currPrimaryColor);
        markSelectedSecondary(currSecondaryColor);
    }

    /**
     * Activate when user clicks on specific button
     * Should change primary and secondary color based on button clicked.
     * @param view
     */
    @Override
    public void onClick(View view) {
        // if tapped button is save theme, saving selected primary and secondary colors.
        if (view.getId() == R.id.saveTheme) {
            saveSelection(currPrimaryColor, currSecondaryColor);
        }

        // removing text from previously selected primary color
        deselectPrimary(currPrimaryColor);
        deselectSecondary(currSecondaryColor);

        // updating selected primary/secondary colors.
        switch (view.getId()) {
            case R.id.primRedButton:
                currPrimaryColor = "red";
                break;
            case R.id.primOrangeButton:
                currPrimaryColor = "orange";
                break;
            case R.id.primGreenButton:
                currPrimaryColor = "green";
                break;
            case R.id.primTealButton:
                currPrimaryColor = "teal";
                break;
            case R.id.primBlueButton:
                currPrimaryColor = "blue";
                break;
            case R.id.primPurpleButton:
                currPrimaryColor = "purple";
                break;
            case R.id.secBlackButton:
                currSecondaryColor = "black";
                break;
            case R.id.secWhiteButton:
                currSecondaryColor = "white";
                break;
            case R.id.saveTheme:
                saveSelection(currPrimaryColor,currSecondaryColor);
                break;
        }

        // updating text on selected primary/secondary color
        markSelectedPrimary(currPrimaryColor);
        markSelectedSecondary(currSecondaryColor);
    }

    /**
     * Save User's preferred theme based on selected primary and secondary colors.
     * Return values to login activity for account creation
     * Update database  and shared preferences if originally from main activity.
     * @param primColor
     * @param secondaryColor
     */
    public void saveSelection(String primColor, String secondaryColor){
        if(username == null){
            Intent resultIntent = new Intent();

            String[] colors = new String[2];
            colors[0] = primColor;
            colors[1] = secondaryColor;

            ThemeUtil.getInstance().setPreferredTheme(primColor,secondaryColor);
            ThemeUtil.getInstance().hasUserSelected = true;

            resultIntent.putStringArrayListExtra("colors", new ArrayList<>(Arrays.asList(colors)));
            setResult(Activity.RESULT_OK, resultIntent);

            finish();
            return;
        }

        ThemeUtil.getInstance().setPreferredTheme(primColor,secondaryColor);
        userService.updateTheme(username, primColor, secondaryColor);
        Intent resultIntent = new Intent();
        setResult(Activity.RESULT_OK, resultIntent);
        finish();

    }

    /**
     * Update selected primary button text in UI.
     * Note: 2 separate functions to distinguish primary and secondary colors (same color might be added to both)
     * @param color
     */
    public void markSelectedPrimary(String color){

        switch (color){
            case "red":
                primaryRedButton.setText("SELECTED");
                primaryRedButton.setTextColor(ThemeUtil.getColorInt(currSecondaryColor));
                break;
            case "orange":
                primaryOrangeButton.setText("SELECTED");
                primaryOrangeButton.setTextColor(ThemeUtil.getColorInt(currSecondaryColor));
                break;
            case "green":
                primaryGreenButton.setText("SELECTED");
                primaryGreenButton.setTextColor(ThemeUtil.getColorInt(currSecondaryColor));
                break;
            case "teal":
                primaryTealButton.setText("SELECTED");
                primaryTealButton.setTextColor(ThemeUtil.getColorInt(currSecondaryColor));
                break;
            case "blue":
                primaryBlueButton.setText("SELECTED");
                primaryBlueButton.setTextColor(ThemeUtil.getColorInt(currSecondaryColor));
                break;
            case "purple":
                primaryPurpleButton.setText("SELECTED");
                primaryPurpleButton.setTextColor(ThemeUtil.getColorInt(currSecondaryColor));
                break;
        }
    }

    /**
     * deselect previously selected primary color
     * @param color
     */
    public void deselectPrimary(String color){
        switch (color){
            case "red":
                primaryRedButton.setText("");
                break;
            case "orange":
                primaryOrangeButton.setText("");
                break;
            case "green":
                primaryGreenButton.setText("");
                break;
            case "teal":
                primaryTealButton.setText("");
                break;
            case "blue":
                primaryBlueButton.setText("");
                break;
            case "purple":
                primaryPurpleButton.setText("");
                break;
        }
    }

    /**
     * Update selected secondary button text in UI.
     * Note: 2 separate functions to distinguish primary and secondary colors (same color might be added to both)
     * @param color
     */
    public void markSelectedSecondary(String color){
        switch (color){
            case "black":
                secondaryBlackButton.setText("SELECTED");
                break;
            case "white":
                secondaryWhiteButton.setText("SELECTED");
                break;
        }
    }

    /**
     * deselect previously selected secondary color
     * @param color
     */
    public void deselectSecondary(String color){
        switch (color){
            case "black":
                secondaryBlackButton.setText("");
                break;
            case "white":
                secondaryWhiteButton.setText("");
                break;
        }
    }

}
