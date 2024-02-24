package edu.uiuc.cs427app.util;

import android.graphics.Color;
import android.content.SharedPreferences;

import androidx.preference.PreferenceManager;

import java.util.Arrays;
import java.util.List;

import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.WeatherApp;


public class ThemeUtil {
    private static ThemeUtil INSTANCE = null;
    public static Boolean hasUserSelected = false;
    private static SharedPreferences app_preferences;

    // private constructor that will be accessed inside this class only
    private ThemeUtil() {
        app_preferences = PreferenceManager.getDefaultSharedPreferences(WeatherApp.getAppContext());
    }

    // synchronized keyword to make it thread safe
    // static method to access the object
    public static synchronized ThemeUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ThemeUtil();
        }
        return(INSTANCE);
    }

    //Returns the preferred theme stored in shared_preferences.
    //Use setTheme() with the return of this function
    public static int getPreferredTheme() {
        String currPrimaryColor = app_preferences.getString("primaryColor", "purple");
        String currSecondaryColor = app_preferences.getString("secondaryColor", "teal");

        String theme = currPrimaryColor.toLowerCase() + currSecondaryColor.toLowerCase();

        switch(theme) {
            case "purplewhite" : {
                return R.style.Theme_PurpleWhiteTheme;
            }
            case "redwhite" : {
                return R.style.Theme_RedWhiteTheme;
            }
            case "orangewhite" : {
                return R.style.Theme_OrangeWhiteTheme;
            }
            case "bluewhite" : {
                return R.style.Theme_BlueWhiteTheme;
            }
            case "greenwhite" : {
                return R.style.Theme_GreenWhiteTheme;
            }
            case "tealwhite" : {
                return R.style.Theme_TealWhiteTheme;
            }
            case "purpleblack" : {
                return R.style.Theme_PurpleBlackTheme;
            }
            case "redblack" : {
                return R.style.Theme_RedBlackTheme;
            }
            case "orangeblack" : {
                return R.style.Theme_OrangeBlackTheme;
            }
            case "blueblack" : {
                return R.style.Theme_BlueBlackTheme;
            }
            case "greenblack" : {
                return R.style.Theme_GreenBlackTheme;
            }
            case "tealblack" : {
                return R.style.Theme_TealBlackTheme;
            }
        }

        return R.style.Theme_PurpleWhiteTheme;
    }

    //Return Color Int values from string color
    public static int getColorInt(String color){
        switch(color) {
            case "black": {
                return Color.BLACK;
            }
            case "white": {
                return Color.WHITE;
            }
        }

        return R.color.white;
    }

    //Store preferred primary and secondary colors (for theme) in shared preferences for easy access
    public static void setPreferredTheme(String primaryColor, String secondaryColor) {
        app_preferences.edit().putString("primaryColor", primaryColor).apply();
        app_preferences.edit().putString("secondaryColor", secondaryColor).apply();
    }

    //Get preferred color themes to use in ThemeActivity to find currently selected colors.
    public static List<String> getPreferredColors() {
        String currPrimaryColor = app_preferences.getString("primaryColor", "purple");
        String currSecondaryColor = app_preferences.getString("secondaryColor", "teal");

        List<String> colors = Arrays.asList(currPrimaryColor, currSecondaryColor);
        return colors;
    }
}
