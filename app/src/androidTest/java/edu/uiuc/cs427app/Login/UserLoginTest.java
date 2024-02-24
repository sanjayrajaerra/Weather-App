package edu.uiuc.cs427app.Login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uiuc.cs427app.Activity.LoginActivity;
import edu.uiuc.cs427app.Activity.MainActivity;
import edu.uiuc.cs427app.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserLoginTest {
    @Rule
    public ActivityScenarioRule<LoginActivity> activityRule =
            new ActivityScenarioRule(LoginActivity.class);

    @Before
    public void setUp() {
        // Initialize Intents before the test
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Intents after the test
        Intents.release();
    }
    @Test
    public void testUserLogin_Unregistered_User()
    {
        onView(ViewMatchers.withId(R.id.username)).perform(typeText("testuser"));
        onView(withId(R.id.password)).perform(typeText("testpassword"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signin_button)).perform(click());
        SystemClock.sleep(500);
        // Add assertions to verify the expected behavior
//        onView(withId(R.id.welcomeMessageTextView)).check(matches(isDisplayed()));
        onView(withText("User does not exist, please check if you have entered correct credentials."))// Ensure that it's a dialog
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUserLogin_wrong_credentials()
    {
        onView(withId(R.id.username)).perform(typeText(""));
        onView(withId(R.id.password)).perform(typeText("testpassword"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signin_button)).perform(click());
        SystemClock.sleep(500);
        // Add assertions to verify the expected behavior
//        onView(withId(R.id.welcomeMessageTextView)).check(matches(isDisplayed()));
        onView(withText("Please enter valid username and password"))// Ensure that it's a dialog
                .inRoot(isDialog())
                .check(matches(isDisplayed()));
    }

    @Test
    public void testUserLogin_Registered_User() {
        onView(withId(R.id.username)).perform(typeText("TestUser"));
        onView(withId(R.id.password)).perform(typeText("TestUser"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signin_button)).perform(click());
        SystemClock.sleep(500);
        intended(hasComponent(MainActivity.class.getName()));
    }

}
