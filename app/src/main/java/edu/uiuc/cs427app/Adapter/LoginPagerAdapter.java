package edu.uiuc.cs427app.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import edu.uiuc.cs427app.Fragment.Login.SigninTabFragment;
import edu.uiuc.cs427app.Fragment.Login.SignupTabFragment;

public class LoginPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    Integer totalNumberOfPages = 2;

    /**
     * The parameterized constructor for the LoginPagerAdapter
     * @param fm The fragment manager
     * @param context The application context
     * @param totalNumberOfPages The number of pages
     */
    public LoginPagerAdapter(@NonNull FragmentManager fm, Context context, Integer totalNumberOfPages) {
        super(fm);
        this.context = context;
        this.totalNumberOfPages = totalNumberOfPages;
    }

    // getItem -> Fragment
    // returns fragment at index. 0: signin 1: signup
    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                return new SignupTabFragment();
            default:
                return new SigninTabFragment();
        }
    }

    // getCount -> int
    // returns total number of pages.
    @Override
    public int getCount() {
        return totalNumberOfPages;
    }
}
