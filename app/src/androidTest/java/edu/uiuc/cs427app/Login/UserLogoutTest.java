package edu.uiuc.cs427app.Login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.os.SystemClock;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.uiuc.cs427app.Activity.LoginActivity;
import edu.uiuc.cs427app.R;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserLogoutTest {
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
    public void testUserLogout_LoggedInUser()
    {
        //first, log a registered user in
        onView(withId(R.id.username)).perform(typeText("TestUser"));
        onView(withId(R.id.password)).perform(typeText("TestUser"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.signin_button)).perform(click());
        SystemClock.sleep(500);
        //confirm that clicking Logout takes user back to sign-in page
        onView(withId(R.id.LogOutButton)).perform(click());
        SystemClock.sleep(500);
        onView(withId(R.id.signin_button)).check(matches(isDisplayed()));
    }
}
