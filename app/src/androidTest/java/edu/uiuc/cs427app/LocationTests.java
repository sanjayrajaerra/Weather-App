package edu.uiuc.cs427app;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Intent;
import android.os.SystemClock;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.google.gson.Gson;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import edu.uiuc.cs427app.Activity.DetailsActivity;
import edu.uiuc.cs427app.model.CityModel;

@RunWith(Enclosed.class)
public class LocationTests {

    // Location tests for city 1 (Champaign)
    public static class ChampaignTests {
        // Creating Details Activity With Champaign as the selected city
        static Intent intentChampaign;
        static {
            intentChampaign = new Intent(WeatherApp.getAppContext(), DetailsActivity.class);
            Gson gson = new Gson();
            String json = gson.toJson(new CityModel("Champaign", "IL", "US", 40.116421f, -88.243385f));
            intentChampaign.putExtra("city", json);
            intentChampaign.putExtra("username","WeatherTestUser");
        }

        @Rule
        public ActivityScenarioRule<DetailsActivity> activityRuleChampaign =
                new ActivityScenarioRule(intentChampaign);

        @Before
        public void setUp() {
            Intents.init();
        }

        @After
        public void tearDown() {
            // Release Intents after the test
            SystemClock.sleep(1000);
            Intents.release();
        }
        @Test
        public void loadDetailsActivity_Test() {
            //view assert in check
            onView(withId(R.id.welcomeText)).check(matches(withText("Welcome to Champaign")));
        }
        @Test
        public void champaignLocation_Test() {
            // open map
            SystemClock.sleep(1000);
            onView(withId(R.id.mapButton)).perform(click());

            // check coordinates
            onView(withId(R.id.latitude)).check(matches(withText("Latitude: 40.116421")));
            onView(withId(R.id.longitude)).check(matches(withText("Longitude: -88.243385")));

            SystemClock.sleep(2000);

            // check map
            onView(withId(R.id.map_view)).check(matches(isDisplayed()));
        }
    }

    // Location tests for city 2 (Dallas)
    public static class DallasTests {
        // Creating Details Activity With Dallas as the selected city
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
        public void loadDetailsActivity_Test(){
            //view assert in check
            onView(withId(R.id.welcomeText)).check(matches(withText("Welcome to Dallas")));

        }
        @Test
        public void DallasLocation_Test() {
            // open map
            SystemClock.sleep(1000);
            onView(withId(R.id.mapButton)).perform(click());

            // check coordinates
            onView(withId(R.id.latitude)).check(matches(withText("Latitude: 29.749907")));
            onView(withId(R.id.longitude)).check(matches(withText("Longitude: -95.358421")));

            // wait for api to send map
            SystemClock.sleep(2000);

            // check map
            onView(withId(R.id.map_view)).check(matches(isDisplayed()));
        }
    }
}