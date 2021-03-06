package com.example.uman_android_project.fragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MainFragmentAdapter extends FragmentPagerAdapter {
    public MainFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                fragment = new AddTreeFragment();
                break;
            case 2:
                fragment = new AddOrderFragment();
                break;
            case 3:
                fragment = new ProfileFragment();
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
