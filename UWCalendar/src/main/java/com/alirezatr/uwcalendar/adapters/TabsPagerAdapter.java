package com.alirezatr.uwcalendar.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.alirezatr.uwcalendar.fragments.CourseInfoFragment;
import com.alirezatr.uwcalendar.fragments.CourseScheduleFragment;

import java.util.HashMap;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    public String course;
    private HashMap pageReference = new HashMap<Integer, Fragment>();

    public TabsPagerAdapter(FragmentManager fm, String course) {
        super(fm);
        this.course = course;
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                Bundle bundle = new Bundle();
                bundle.putString("course", course);
                Fragment fragment = new CourseInfoFragment();
                fragment.setArguments(bundle);
                pageReference.put(1, fragment);
                return fragment;

            case 1:
                Fragment fragment2 =  new CourseScheduleFragment();
                pageReference.put(2, fragment2);
                return fragment2;
        }
        return null;
    }

    public Fragment getFragment(int key) {
        return (Fragment) pageReference.get(key);
    }

    @Override
    public int getCount() {
        return 2;
    }

}
