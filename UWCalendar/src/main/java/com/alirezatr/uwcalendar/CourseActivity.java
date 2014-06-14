package com.alirezatr.uwcalendar;

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

import com.alirezatr.uwcalendar.Listeners.CourseClassListener;
import com.alirezatr.uwcalendar.Listeners.CourseListener;
import com.alirezatr.uwcalendar.Models.Course;
import com.alirezatr.uwcalendar.Models.CourseClass;
import com.alirezatr.uwcalendar.Network.NetworkManager;

import java.util.ArrayList;

/**
 * Created by ali on 1/20/2014.
 */
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
            subject = extras.getString("subject");
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
                TextView description = (TextView) findViewById(R.id.description);
                description.setText(course.getDescription());

                TextView instructions = (TextView) findViewById(R.id.instructions);
                instructions.setText(course.getInstructions().toString());

                TextView prerequisites = (TextView) findViewById(R.id.prerequisites);
                TextView prerequisitesTitle = (TextView) findViewById(R.id.prerequisites_title);
                prerequisitesTitle.setText("Prerequisites");
                if (course.getPrerequisites() == "null") {
                    prerequisites.setText("none");
                }
                else {
                    prerequisites.setText(course.getPrerequisites());
                }

                TextView antirequisites = (TextView) findViewById(R.id.antirequisites);
                TextView antirequisitesTitle = (TextView) findViewById(R.id.antirequisites_title);
                antirequisitesTitle.setText("Antirequisites");
                if (course.getAntirequisites() == "null") {
                    antirequisites.setText("none");
                }
                else {
                    antirequisites.setText(course.getAntirequisites());
                }

                if (course.getNotes() != "null") {
                    TextView notes = (TextView) findViewById(R.id.notes);
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
        networkManager.getCourseClass(subject, catalog_number, new CourseClassListener() {
            @Override
            public void onSuccess(ArrayList<CourseClass> courseClass) {
                LinearLayout layout = (LinearLayout) findViewById(R.id.linLayout);
                for(CourseClass classes: courseClass) {
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
                                classes.getStart_time() + " - " + classes.getEnd_time() + " " + classes.getLocation()+classes.getRoom());
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
