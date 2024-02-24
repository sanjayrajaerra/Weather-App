package edu.uiuc.cs427app.Login;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.UUID;

import edu.uiuc.cs427app.Activity.LoginActivity;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.util.ThemeUtil;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class UserRegistrationTest {
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

        ThemeUtil.getInstance().hasUserSelected = false;
    }

    @Test
    public void testUserRegistration_newUser() {
        ViewInteraction tabView = onView(
                Matchers.allOf(withContentDescription("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        Matchers.allOf(ViewMatchers.withId(R.id.tab_layout)),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        SystemClock.sleep(500);

        ViewInteraction usernameTextField = onView(
                Matchers.allOf(withId(R.id.signup_username),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                0),
                        isDisplayed()));
        usernameTextField.perform(replaceText(UUID.randomUUID().toString()), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction passwordTextField = onView(
                Matchers.allOf(withId(R.id.signup_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                1),
                        isDisplayed()));
        passwordTextField.perform(replaceText("admin"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction confirmPasswordTextField = onView(
                Matchers.allOf(withId(R.id.confirm_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                2),
                        isDisplayed()));
        confirmPasswordTextField.perform(replaceText("admin"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction selectThemeButton = onView(
                Matchers.allOf(withId(R.id.select_theme_button), withText("SELECT PREFERRED THEME"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                3),
                        isDisplayed()));
        selectThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction primRedThemeButton = onView(
                Matchers.allOf(withId(R.id.primRedButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.details),
                                        2),
                                0),
                        isDisplayed()));
        primRedThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction secBlackThemeButton = onView(
                Matchers.allOf(withId(R.id.secBlackButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.details),
                                        4),
                                0),
                        isDisplayed()));
        secBlackThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction saveThemeButton = onView(
                Matchers.allOf(withId(R.id.saveTheme), withText("Save Theme"),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.details),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                5),
                        isDisplayed()));
        saveThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction signUpButton = onView(
                Matchers.allOf(withId(R.id.signup_button), withText("SIGN UP"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager), withContentDescription("login screen view pager"))),
                                4),
                        isDisplayed()));
        signUpButton.perform(click());

        SystemClock.sleep(500);


        // Add assertions to verify the expected behavior
        ViewInteraction alertDialog = onView(withText("Registered Successfully, Please sign-in"))
                .inRoot(isDialog());

        alertDialog.check(matches(isDisplayed()));
    }


    @Test
    public void testUserRegistration_passwordMismatch() {
        ViewInteraction tabView = onView(
                Matchers.allOf(withContentDescription("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        Matchers.allOf(withId(R.id.tab_layout)),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        SystemClock.sleep(500);

        ViewInteraction usernameTextField = onView(
                Matchers.allOf(withId(R.id.signup_username),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                0),
                        isDisplayed()));
        usernameTextField.perform(replaceText("admin"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction passwordTextField = onView(
                Matchers.allOf(withId(R.id.signup_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                1),
                        isDisplayed()));
        passwordTextField.perform(replaceText("adm1n"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction confirmPasswordTextField = onView(
                Matchers.allOf(withId(R.id.confirm_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                2),
                        isDisplayed()));
        confirmPasswordTextField.perform(replaceText("admin"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction selectThemeButton = onView(
                Matchers.allOf(withId(R.id.select_theme_button), withText("SELECT PREFERRED THEME"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                3),
                        isDisplayed()));
        selectThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction primRedThemeButton = onView(
                Matchers.allOf(withId(R.id.primRedButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.details),
                                        2),
                                0),
                        isDisplayed()));
        primRedThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction secBlackThemeButton = onView(
                Matchers.allOf(withId(R.id.secBlackButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.details),
                                        4),
                                0),
                        isDisplayed()));
        secBlackThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction saveThemeButton = onView(
                Matchers.allOf(withId(R.id.saveTheme), withText("Save Theme"),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.details),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                5),
                        isDisplayed()));
        saveThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction signUpButton = onView(
                Matchers.allOf(withId(R.id.signup_button), withText("SIGN UP"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager), withContentDescription("login screen view pager"))),
                                4),
                        isDisplayed()));
        signUpButton.perform(click());

        SystemClock.sleep(500);

        // Add assertions to verify the expected behavior
        ViewInteraction alertDialog = onView(withText("Password does not match, please re-enter your password"))
                .inRoot(isDialog());

        alertDialog.check(matches(isDisplayed()));
    }

    @Test
    public void testUserRegistration_themeSelectionMissing() {
        ViewInteraction tabView = onView(
                Matchers.allOf(withContentDescription("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        Matchers.allOf(withId(R.id.tab_layout)),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        SystemClock.sleep(500);

        ViewInteraction usernameTextField = onView(
                Matchers.allOf(withId(R.id.signup_username),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                0),
                        isDisplayed()));
        usernameTextField.perform(replaceText("admin1"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction passwordTextField = onView(
                Matchers.allOf(withId(R.id.signup_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                1),
                        isDisplayed()));
        passwordTextField.perform(replaceText("admin"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction confirmPasswordTextField = onView(
                Matchers.allOf(withId(R.id.confirm_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                2),
                        isDisplayed()));
        confirmPasswordTextField.perform(replaceText("admin"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction signUpButton = onView(
                Matchers.allOf(withId(R.id.signup_button), withText("SIGN UP"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager), withContentDescription("login screen view pager"))),
                                4),
                        isDisplayed()));
        signUpButton.perform(click());

        SystemClock.sleep(500);


        // Add assertions to verify the expected behavior
        ViewInteraction alertDialog = onView(withText("Please select a theme you like"))
                .inRoot(isDialog());

        alertDialog.check(matches(isDisplayed()));
    }

    @Test
    public void testUserRegistration_existingUser() {
        ViewInteraction tabView = onView(
                Matchers.allOf(withContentDescription("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        Matchers.allOf(withId(R.id.tab_layout)),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        SystemClock.sleep(500);

        ViewInteraction usernameTextField = onView(
                Matchers.allOf(withId(R.id.signup_username),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                0),
                        isDisplayed()));
        usernameTextField.perform(replaceText("TestUser"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction passwordTextField = onView(
                Matchers.allOf(withId(R.id.signup_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                1),
                        isDisplayed()));
        passwordTextField.perform(replaceText("TestUser"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction confirmPasswordTextField = onView(
                Matchers.allOf(withId(R.id.confirm_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                2),
                        isDisplayed()));
        confirmPasswordTextField.perform(replaceText("TestUser"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction selectThemeButton = onView(
                Matchers.allOf(withId(R.id.select_theme_button), withText("SELECT PREFERRED THEME"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                3),
                        isDisplayed()));
        selectThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction primRedThemeButton = onView(
                Matchers.allOf(withId(R.id.primRedButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.details),
                                        2),
                                0),
                        isDisplayed()));
        primRedThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction secBlackThemeButton = onView(
                Matchers.allOf(withId(R.id.secBlackButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.details),
                                        4),
                                0),
                        isDisplayed()));
        secBlackThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction saveThemeButton = onView(
                Matchers.allOf(withId(R.id.saveTheme), withText("Save Theme"),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.details),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                5),
                        isDisplayed()));
        saveThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction signUpButton = onView(
                Matchers.allOf(withId(R.id.signup_button), withText("SIGN UP"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager), withContentDescription("login screen view pager"))),
                                4),
                        isDisplayed()));
        signUpButton.perform(click());

        SystemClock.sleep(500);

        // Add assertions to verify the expected behavior
        ViewInteraction alertDialog = onView(withText("Username already exists, please try another username"))
                .inRoot(isDialog());

        alertDialog.check(matches(isDisplayed()));
    }

    @Test
    public void testUserRegistration_validInput() {
        ViewInteraction tabView = onView(
                Matchers.allOf(withContentDescription("Sign Up"),
                        childAtPosition(
                                childAtPosition(
                                        Matchers.allOf(withId(R.id.tab_layout)),
                                        0),
                                1),
                        isDisplayed()));
        tabView.perform(click());

        SystemClock.sleep(500);

        ViewInteraction usernameTextField = onView(
                Matchers.allOf(withId(R.id.signup_username),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                0),
                        isDisplayed()));
        usernameTextField.perform(replaceText(""), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction passwordTextField = onView(
                Matchers.allOf(withId(R.id.signup_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                1),
                        isDisplayed()));
        passwordTextField.perform(replaceText("admin"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction confirmPasswordTextField = onView(
                Matchers.allOf(withId(R.id.confirm_password),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                2),
                        isDisplayed()));
        confirmPasswordTextField.perform(replaceText("admin"), closeSoftKeyboard());

        SystemClock.sleep(500);

        ViewInteraction selectThemeButton = onView(
                Matchers.allOf(withId(R.id.select_theme_button), withText("SELECT PREFERRED THEME"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager))),
                                3),
                        isDisplayed()));
        selectThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction primRedThemeButton = onView(
                Matchers.allOf(withId(R.id.primRedButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.details),
                                        2),
                                0),
                        isDisplayed()));
        primRedThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction secBlackThemeButton = onView(
                Matchers.allOf(withId(R.id.secBlackButton),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.details),
                                        4),
                                0),
                        isDisplayed()));
        secBlackThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction saveThemeButton = onView(
                Matchers.allOf(withId(R.id.saveTheme), withText("Save Theme"),
                        childAtPosition(
                                Matchers.allOf(withId(R.id.details),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                0)),
                                5),
                        isDisplayed()));
        saveThemeButton.perform(click());

        SystemClock.sleep(500);

        ViewInteraction signUpButton = onView(
                Matchers.allOf(withId(R.id.signup_button), withText("SIGN UP"),
                        childAtPosition(
                                withParent(Matchers.allOf(withId(R.id.view_pager), withContentDescription("login screen view pager"))),
                                4),
                        isDisplayed()));
        signUpButton.perform(click());

        SystemClock.sleep(500);

        // Add assertions to verify the expected behavior
        ViewInteraction alertDialog = onView(withText("Please enter valid username and password"))
                .inRoot(isDialog());

        alertDialog.check(matches(isDisplayed()));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}

