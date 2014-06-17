package com.alirezatr.uwcalendar.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.listeners.ClassesListener;
import com.alirezatr.uwcalendar.listeners.CourseListener;
import com.alirezatr.uwcalendar.models.*;
import com.alirezatr.uwcalendar.models.Class;
import com.alirezatr.uwcalendar.network.NetworkManager;

import java.util.ArrayList;

public class CourseActivity extends Activity {
    private NetworkManager networkManager;
    private ProgressDialog dialog;
    private String subject;
    private String catalog_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course);

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        networkManager = new NetworkManager(this);
        dialog = new ProgressDialog(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subject = extras.getString("SUBJECT");
            catalog_number = extras.getString("catalog_number");
            actionBar.setTitle(subject + catalog_number);
            actionBar.setSubtitle("Waterloo Calendar");
            loadCourse(subject, catalog_number);
        }
    }

    public void loadCourse(final String subject, final String catalog_number) {
        dialog.setMessage("Loading course");
        dialog.show();
        networkManager.getCourse(subject, catalog_number, new CourseListener() {
            @Override
            public void onSuccess(Course course) {
                TextView description = (TextView) findViewById(R.id.course_description);
                description.setText(course.getDescription());

                TextView instructions = (TextView) findViewById(R.id.course_instructions);
                instructions.setText(course.getInstructions().toString());

                TextView prerequisites = (TextView) findViewById(R.id.course_prerequisites);
                TextView prerequisitesTitle = (TextView) findViewById(R.id.course_prerequisites_title);
                prerequisitesTitle.setText("Prerequisites");
                if (course.getPrerequisites() == "null") {
                    prerequisites.setText("none");
                }
                else {
                    prerequisites.setText(course.getPrerequisites());
                }

                TextView antirequisites = (TextView) findViewById(R.id.course_antirequisites);
                TextView antirequisitesTitle = (TextView) findViewById(R.id.course_antirequisites_title);
                antirequisitesTitle.setText("Antirequisites");
                if (course.getAntirequisites() == "null") {
                    antirequisites.setText("none");
                }
                else {
                    antirequisites.setText(course.getAntirequisites());
                }

                if (course.getNotes() != "null") {
                    TextView notes = (TextView) findViewById(R.id.course_notes);
                    notes.setText(course.getNotes());
                }

                loadCourseClass(subject, catalog_number);
            }

            @Override
            public void onError(Exception error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error loading course, please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadCourseClass(String subject, String catalog_number) {
        networkManager.getCourseClass(subject, catalog_number, new ClassesListener() {
            @Override
            public void onSuccess(ArrayList<Class> clases) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.linLayout);
                for(com.alirezatr.uwcalendar.models.Class classes: clases) {
                    if (classes.getRoom() != null || classes.getInstructor() != null || classes.getLocation() != null) {
                        LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        TextView tv = new TextView(getApplicationContext());
                        tv.setPadding(30, 5, 30, 0);
                        tv.setTextColor(Color.DKGRAY);
                        if(classes.getLocation() == "null"){
                            classes.setLocation("");
                        }
                        if(classes.getRoom() == "null") {
                            classes.setRoom("");
                        }
                        tv.setText(classes.getSection() + " " + classes.getInstructor() + "\n" + classes.getWeekdays() + " " +
                                classes.getStartTime() + " - " + classes.getEndTime() + " " + classes.getLocation()+classes.getRoom());
                        layout.addView(tv);
                    }
                }
                dialog.dismiss();
            }

            @Override
            public void onError(Exception error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error loading course, please try again later", Toast.LENGTH_LONG).show();
            }
        });
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
                loadCourse(subject, catalog_number);
                break;
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                NavUtils.navigateUpTo(this, upIntent);
                break;
            default:
                break;
        }
        return true;
    }
}
