package edu.uiuc.cs427app;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import edu.uiuc.cs427app.Activity.LoginActivity;
import edu.uiuc.cs427app.Activity.MainActivity;
import edu.uiuc.cs427app.model.CityModel;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.services.CityService;
import edu.uiuc.cs427app.services.UserService;

/**
 * JUnit tests using Espresso for testing the application UI for
 * adding and removing a city to and from a user's selected city list
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class CityTests {
    // The user and city services
    private UserService theUserService;
    private CityService theCityService;

    /**
     * Rule to run prior to any testing @Before definitions
     */
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule(LoginActivity.class);

    /**
     * Initialize intents and user and city services
     */
    @Before
    public void setUp()
    {
        // Initialize Intents before the test
        Intents.init();

        // Get the services
        theUserService = theUserService.getInstance();
        theCityService = theCityService.getInstance();
    }

    /**
     * After every test clear all intents
     */
    @After
    public void tearDown() {
        // Release Intents after the test
        Intents.release();
    }

    /**
     * Tests the user adding a city and ensures the city has been stored in the database for
     * that user
     * @throws InterruptedException
     */
    @Test
    public void addCityTest() throws InterruptedException {
        // Add a test user to the database
        // Generate a random username and password for testing
        String randomTestString = UUID.randomUUID().toString();
        UserModel testUser = new UserModel(randomTestString,randomTestString, false, new ArrayList<>());
        // Add the user to the database
        theUserService.addUser(testUser);
        // Have the test user log in to the application
        onView(withId(R.id.username)).perform(typeText(randomTestString));
        onView(withId(R.id.password)).perform(typeText(randomTestString));
        Espresso.closeSoftKeyboard();
        // Add pause for easier test observation
        Thread.sleep(500);
        onView(withId(R.id.signin_button)).perform(click());
        // Ensure Main Activity is launched
        intended(hasComponent(MainActivity.class.getName()));
        // Add pause for easier test observation
        Thread.sleep(500);
        // Select the Add or Remove A Location
        onView(withId(R.id.buttonAddLocation)).perform(click());
        // Add pause for easier test observation
        Thread.sleep(500);
        // Get the cities from the database which are available to add to a user's list
        List<CityModel> citiesFromDb = theCityService.getAllCities();
        // Type in the city name
        onView(withId(R.id.inputCity)).perform(typeText(citiesFromDb.get(2).getName()));
        Espresso.closeSoftKeyboard();
        // Add pause for easier test observation
        Thread.sleep(500);
        // Select Add to add the City
        onView(withId(R.id.buttonAdd)).perform(click());
        // Select the Back button to navigate back to the main activity to view
        // the user's list of cities
        onView(withId(R.id.buttonBack)).perform(click());
        // Add pause for easier test observation
        Thread.sleep(1000);
        // Ensure Main Activity is launched
        intended(hasComponent(MainActivity.class.getName()));
        // Get the user from the database by username
        UserModel userByUsername = theUserService.getUserByUsername(randomTestString);
        // Assert that the city for the user has been stored in the database
        Assert.assertTrue(theUserService.getCitiesForUser(userByUsername).contains(citiesFromDb.get(2)));
    }

    /**
     * Tests the user removing an existing city from their list
     * and ensures the city that was removed is no longer stored in the database for that user
     * @throws InterruptedException
     */
    @Test
    public void removeCityTest() throws InterruptedException {
        // Add a test user to the database
        // Generate a random username and password for testing
        String randomTestString = UUID.randomUUID().toString();
        UserModel testUser = new UserModel(randomTestString,randomTestString, false, new ArrayList<>());
        // Add the user to the database
        theUserService.addUser(testUser);
        List<CityModel> citiesFromDb = theCityService.getAllCities();

        // Add cities for the user to be stored in the database so that there
        // is an existing city to remove for the user for city removal testing purposes
        theUserService.addCityForUser(testUser, citiesFromDb.get(2));
        theUserService.addCityForUser(testUser, citiesFromDb.get(4));
        theUserService.addCityForUser(testUser, citiesFromDb.get(6));

        // Have the test user log in to the application
        onView(withId(R.id.username)).perform(typeText(randomTestString));
        onView(withId(R.id.password)).perform(typeText(randomTestString));
        Espresso.closeSoftKeyboard();
        // Add pause for easier test observation
        Thread.sleep(500);
        onView(withId(R.id.signin_button)).perform(click());
        // Ensure Main Activity is launched
        intended(hasComponent(MainActivity.class.getName()));
        // Add pause for easier test observation
        Thread.sleep(500);

        // ------------ Removal of an Existing City --------------
        // Select the Add or Remove A Location
        onView(withId(R.id.buttonAddLocation)).perform(click());
        // Add pause for easier test observation
        Thread.sleep(500);
        // Type in the city name
        onView(withId(R.id.inputCity)).perform(typeText(citiesFromDb.get(4).getName()));
        Espresso.closeSoftKeyboard();
        // Add pause for easier test observation
        Thread.sleep(500);
        // Select Add to add the City
        onView(withId(R.id.buttonRemove)).perform(click());
        // Select the Back button to navigate back to the main activity to view
        // the user's list of cities
        onView(withId(R.id.buttonBack)).perform(click());
        // Add pause for easier test observation
        Thread.sleep(1000);
        // Ensure Main Activity is launched
        intended(hasComponent(MainActivity.class.getName()));
        // Assert that the city for the user not longer exists in the database
        Assert.assertFalse(theUserService.getCitiesForUser(testUser).contains(citiesFromDb.get(4)));
        // Add pause for easier test observation
        Thread.sleep(500);
    }
}
