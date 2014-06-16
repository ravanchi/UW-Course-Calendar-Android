package com.alirezatr.uwcalendar.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.CoursesAdapter;
import com.alirezatr.uwcalendar.listeners.CoursesListener;
import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.network.NetworkManager;

import java.util.ArrayList;

public class CoursesActivity extends ListActivity{
    private NetworkManager networkManager;
    private ProgressDialog dialog;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        networkManager = new NetworkManager(this);
        dialog = new ProgressDialog(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subject = extras.getString("subject");
            actionBar.setTitle(subject);
            actionBar.setSubtitle("Waterloo Calendar");
            loadCourses(subject);
        }
    }

    public void loadCourses(String subject) {
        dialog.setMessage("Loading courses");
        dialog.show();
        networkManager.getCourses(subject, new CoursesListener() {
            @Override
            public void onSuccess(ArrayList<Course> courses) {
                if(courses.size() == 0) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "No courses to load for this subject", Toast.LENGTH_LONG).show();
                } else {
                    setListAdapter(new CoursesAdapter(getApplicationContext(), courses));
                    dialog.dismiss();
                }
            }

            @Override
            public void onError(Exception error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error loading courses, please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        Course course = (Course) o;
        Intent intent = new Intent(getListView().getContext(), CourseActivity.class);
        intent.putExtra("subject", course.getSubject());
        intent.putExtra("catalog_number", course.getCatalogNumber());
        getListView().getContext().startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_load:
                loadCourses(subject);
                break;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            default:
                break;
        }
        return true;
    }
}
