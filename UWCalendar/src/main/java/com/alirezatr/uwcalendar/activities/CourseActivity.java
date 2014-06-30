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
import android.widget.ImageView;
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
    private ProgressDialog mProgressDialog;
    private String subject;
    private String catalog_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.course);

        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.actionbar);
        actionBar.setSubtitle("Waterloo Calendar");
        actionBar.setDisplayHomeAsUpEnabled(true);

        ImageView view = (ImageView)findViewById(android.R.id.home);
        view.setPadding(5, 0, 10, 0);

        networkManager = new NetworkManager(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            subject = extras.getString("subject");
            catalog_number = extras.getString("catalog_number");
            actionBar.setTitle(subject + catalog_number);
            loadCourse(subject, catalog_number);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    public void loadCourse(final String subject, final String catalog_number) {
        mProgressDialog.setMessage("Loading course");
        mProgressDialog.show();
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
                mProgressDialog.dismiss();
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
                mProgressDialog.dismiss();
            }

            @Override
            public void onError(Exception error) {
                mProgressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error loading course, please try again later", Toast.LENGTH_LONG).show();
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
