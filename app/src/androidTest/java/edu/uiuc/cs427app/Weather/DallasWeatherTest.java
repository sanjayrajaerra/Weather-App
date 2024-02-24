package edu.uiuc.cs427app.Weather;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uiuc.cs427app.Activity.DetailsActivity;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.WeatherApp;
import edu.uiuc.cs427app.model.CityModel;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DallasWeatherTest {

    //Creating Details Activity With Dallas as the selected city
    static Intent intentDallas;
    static {
        intentDallas = new Intent(WeatherApp.getAppContext(), DetailsActivity.class);
        Gson gson = new Gson();
        String json = gson.toJson(new CityModel("Dallas", "TX", "US", 29.749907f, -95.358421f));
        intentDallas.putExtra("city", json);
        intentDallas.putExtra("username","WeatherTestUser");
    }
    @Rule
    public ActivityScenarioRule<DetailsActivity> activityRuleDallas =
            new ActivityScenarioRule(intentDallas);

    @Before
    public void setUp() {
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Intents after the test
        Intents.release();
    }

    @Test
    public void city2Weather_Test() {

        //open weather
        onView(withId(R.id.weatherButton)).perform(click());

        //check name
        onView(withId(R.id.textCity)).check(matches(withText("Dallas")));
        SystemClock.sleep(500);
        //check weather is displayed
        onView(withId(R.id.textTemperature)).check(matches(isDisplayed()));

        //make sure value is no longer the default 'Loading'. Value is replaced with weather values
        //Give API time to respond
        SystemClock.sleep(2000);
        onView(withId(R.id.textTemperature)).check(matches(not(withText("Loading"))));
    }
}
