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
import com.alirezatr.uwcalendar.adapters.AlphabetListAdapter;
import com.alirezatr.uwcalendar.adapters.CoursesAdapter;
import com.alirezatr.uwcalendar.listeners.CoursesListener;
import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.models.Subject;
import com.alirezatr.uwcalendar.network.NetworkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CoursesActivity extends ListActivity {
    private AlphabetListAdapter adapter = new AlphabetListAdapter();
    private NetworkManager networkManager;
    private ProgressDialog dialog;
    private String subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_alphabet);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        networkManager = new NetworkManager(this);
        dialog = new ProgressDialog(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subject = extras.getString("SUBJECT");
            actionBar.setTitle(subject);
            actionBar.setSubtitle("Waterloo Calendar");
            loadCourses(subject);
        }
    }

    private void setAdapter(ArrayList<Course> courses) {
        List rows = new ArrayList();
        Course course;

        for(int i = 0; i < courses.size(); i++) {
            course = courses.get(i);
            rows.add(new AlphabetListAdapter.Item(course.getTitle(), course.getDescription()));
        }

        adapter.setRows(rows);
        setListAdapter(adapter);
    }

    public void loadCourses(String subject) {
        dialog.setMessage("Loading courses");
        dialog.show();
        networkManager.getCourses(subject, new CoursesListener() {
            @Override
            public void onSuccess(ArrayList<Course> courses) {
                if(courses.size() == 0) {
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "No courses to load for this SUBJECT", Toast.LENGTH_LONG).show();
                } else {
                    setAdapter(courses);
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
        intent.putExtra("SUBJECT", course.getSubject());
        intent.putExtra("catalog_number", course.getCatalogNumber());
        getListView().getContext().startActivity(intent);
    }
}
