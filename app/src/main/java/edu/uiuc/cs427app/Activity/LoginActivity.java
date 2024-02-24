package edu.uiuc.cs427app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import edu.uiuc.cs427app.Adapter.LoginPagerAdapter;
import edu.uiuc.cs427app.R;
import edu.uiuc.cs427app.WeatherApp;
import edu.uiuc.cs427app.model.UserModel;
import edu.uiuc.cs427app.util.ThemeUtil;

public class LoginActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;

    /**
     * Invoked on activity creation to initialize components
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // updating theme based on selected preferred theme.
        setTheme(ThemeUtil.getInstance().getPreferredTheme());

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // hiding action bar because user hasn't logged in yet
        try {
            this.getSupportActionBar().hide();
        } catch (NullPointerException e) {}

        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);

        tabLayout.addTab(tabLayout.newTab().setText("Sign In"));
        tabLayout.addTab(tabLayout.newTab().setText("Sign Up"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        // creating instance of adapter and referencing viewPager & tabLayout to it.
        final LoginPagerAdapter loginPagerAdapter = new LoginPagerAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(loginPagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));
    }

    // goToHomeActivity -> void
    // input: UserModel
    // redirects user to MainActivity while adding username to intent
    public void goToHomeActivity(UserModel user) {
        setTheme(ThemeUtil.getInstance().getPreferredTheme());

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", user.getUsername());
        startActivity(intent);

        finish();
    }


    // showAlertPopup -> void
    // presents a popup with needed title and message having single continue button
    public void showAlertPopup(String title, String message) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Okay", null).show();
    }
}