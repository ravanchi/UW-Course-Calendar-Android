package com.alirezatr.uwcalendar.activities;

import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.TabsPagerAdapter;
import com.alirezatr.uwcalendar.fragments.CourseDetailFragment;
import com.alirezatr.uwcalendar.fragments.CourseScheduleFragment;
import com.alirezatr.uwcalendar.listeners.ClassesListener;
import com.alirezatr.uwcalendar.listeners.CourseListener;
import com.alirezatr.uwcalendar.models.Class;
import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.network.NetworkClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class CourseActivity extends ActionBarActivity {
    private ActionBar mActionBar;
    private ViewPager mViewPager;
    private TabsPagerAdapter mAdapter;
    private NetworkClient mNetworkClient;

    private String[] mTabLabels = { "Details", "Schedule" };

    private Course course;
    private String courseJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.course_activity);

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

        mNetworkClient = new NetworkClient(this);

        if(course != null) {
            fetchCourse(course.getSubject(), course.getCatalogNumber());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mNetworkClient.cancelAllRequests();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    public void fetchCourse(final String subject, final String catalog_number) {
        mNetworkClient.fetchCourse(subject, catalog_number, new CourseListener() {
            @Override
            public void onSuccess(Course course) {
                TabsPagerAdapter adapter = (TabsPagerAdapter) mViewPager.getAdapter();
                CourseDetailFragment fragment = (CourseDetailFragment) adapter.getFragment(1);
                if (fragment != null) {
                    fragment.populateDetailsView(course);
                }
                fetchCourseClass(course.getSubject(), course.getCatalogNumber());
            }

            @Override
            public void onError(Exception error) {
                TabsPagerAdapter adapter = (TabsPagerAdapter) mViewPager.getAdapter();
                CourseDetailFragment fragment = (CourseDetailFragment) adapter.getFragment(1);
                if (fragment != null) {
                    fragment.showError();
                }
            }
        });
    }

    public void fetchCourseClass(String subject, String catalog_number) {
        mNetworkClient.fetchCourseClass(subject, catalog_number, new ClassesListener() {
            CourseScheduleFragment fragment = (CourseScheduleFragment) mAdapter.getFragment(2);

            @Override
            public void onSuccess(ArrayList<Class> classes) {
                if (fragment != null) {
                    fragment.populateScheduleView(classes);
                }
            }

            @Override
            public void onError(Exception error) {
                TabsPagerAdapter adapter = (TabsPagerAdapter) mViewPager.getAdapter();
                CourseScheduleFragment fragment = (CourseScheduleFragment) adapter.getFragment(2);
                if (fragment != null) {
                    fragment.showError();
                }
            }
        });
    }

    // inflate for action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.menu_load:
                if(mViewPager.getCurrentItem() == 0) {
                    TabsPagerAdapter adapter = (TabsPagerAdapter) mViewPager.getAdapter();
                    CourseDetailFragment fragment = (CourseDetailFragment) adapter.getFragment(1);
                    if(fragment != null) {
                        fragment.showLoading();
                    }
                    if (course != null) {
                        fetchCourse(course.getSubject(), course.getCatalogNumber());
                    }
                } else if(mViewPager.getCurrentItem() == 1) {
                    TabsPagerAdapter adapter = (TabsPagerAdapter) mViewPager.getAdapter();
                    CourseScheduleFragment fragment = (CourseScheduleFragment) adapter.getFragment(2);
                    if(fragment != null) {
                        fragment.showLoading();
                    }
                    if (course != null) {
                        fetchCourseClass(course.getSubject(), course.getCatalogNumber());
                        fragment.showLoading();
                    }
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
