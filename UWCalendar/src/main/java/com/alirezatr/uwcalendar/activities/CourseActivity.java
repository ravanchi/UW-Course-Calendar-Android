package com.alirezatr.uwcalendar.activities;

import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.ImageView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.TabsPagerAdapter;
import com.alirezatr.uwcalendar.fragments.CourseInfoFragment;
import com.alirezatr.uwcalendar.fragments.CourseScheduleFragment;
import com.alirezatr.uwcalendar.listeners.ClassesListener;
import com.alirezatr.uwcalendar.listeners.CourseListener;
import com.alirezatr.uwcalendar.models.Class;
import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.network.NetworkManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CourseActivity extends ActionBarActivity {
    private ActionBar mActionBar;
    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;
    private NetworkManager mNetworkManager;
    private ProgressDialog mProgressDialog;

    private String[] mTabLabels = { "Information", "Schedule" };

    private Course course;
    private String courseJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.course);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                getActionBar().setSelectedNavigationItem(position);
            }
        });
        mActionBar = getSupportActionBar();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseJson = extras.getString("course");
            final Type courseType = new TypeToken<Course>(){}.getType();
            course = new Gson().fromJson(courseJson, courseType);
            mActionBar.setTitle(course.getSubject() + course.getCatalogNumber());
        }

        mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), courseJson);

        mViewPager.setAdapter(mAdapter);

        mActionBar.setIcon(R.drawable.actionbar);
        mActionBar.setSubtitle(getResources().getString(R.string.app_name));
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.TabListener tabListener = new ActionBar.TabListener() {
            public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());
            }

            public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {}

            public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {}
        };

        for (String tab_name : mTabLabels) {
            mActionBar.addTab(mActionBar.newTab().setText(tab_name).setTabListener(tabListener));
        }

        ImageView view = (ImageView)findViewById(android.R.id.home);
        view.setPadding(5, 0, 10, 0);

        mNetworkManager = new NetworkManager(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

        if(course != null) {
            loadCourse(course.getSubject(), course.getCatalogNumber());
        }
    }

    public void loadCourse(final String subject, final String catalog_number) {
        mNetworkManager.getCourse(subject, catalog_number, new CourseListener() {
            @Override
            public void onSuccess(Course course) {
                TabsPagerAdapter adapter = (TabsPagerAdapter) mViewPager.getAdapter();
                CourseInfoFragment fragment = (CourseInfoFragment) adapter.getFragment(1);

                if (fragment != null) {
                    fragment.setView(course);
                }
                loadCourseClass(subject, catalog_number);
            }

            @Override
            public void onError(Exception error) {
                error.printStackTrace();
            }
        });
    }

    public void loadCourseClass(String subject, String catalog_number) {
        mNetworkManager.getCourseClass(subject, catalog_number, new ClassesListener() {
            CourseScheduleFragment fragment = (CourseScheduleFragment) mAdapter.getFragment(2);

            @Override
            public void onSuccess(ArrayList<Class> classes) {
                for (Class courseClass : classes) {
                    if (fragment != null) {
                        fragment.addClassView(courseClass);
                    }
                }
            }

            @Override
            public void onError(Exception error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
