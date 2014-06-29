package com.alirezatr.uwcalendar.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.CoursesListAdapter;
import com.alirezatr.uwcalendar.adapters.SubjectsListAdapter;
import com.alirezatr.uwcalendar.listeners.CoursesListener;
import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.models.Subject;
import com.alirezatr.uwcalendar.network.NetworkManager;
import com.alirezatr.uwcalendar.utils.FilterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class CoursesActivity extends ListActivity {
    private CoursesListAdapter adapter = new CoursesListAdapter();
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
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
        int start = 0;
        int end;
        String previousDigit = null;
        Object[] tmpIndexItem;
        Course course;

        for(int i = 0; i < courses.size(); i++) {
            course = courses.get(i);
            String firstDigit = course.getCatalogNumber().substring(0, 1);

            if (previousDigit != null && !firstDigit.equals(previousDigit)) {
                end = rows.size() - 1;
                tmpIndexItem = new Object[2];
                tmpIndexItem[0] = start;
                tmpIndexItem[1] = end;
                alphabet.add(tmpIndexItem);

                start = end + 1;
            }

            if (!firstDigit.equals(previousDigit)) {
                rows.add(new CoursesListAdapter.Section(subject + firstDigit + "00's"));
                sections.put(subject + firstDigit + "00's", start);
            }

            rows.add(new CoursesListAdapter.Item(subject + course.getCatalogNumber() + " - " + course.getTitle(), course.getDescription()));
            previousDigit = firstDigit;
        }

        if(previousDigit != null) {
            tmpIndexItem = new Object[2];
            tmpIndexItem[0] = start;
            tmpIndexItem[1] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }

        adapter.setRows(rows);
        setListAdapter(adapter);
    }

//    private void setAdapter(ArrayList<Course> courses) {
//        List rows = new ArrayList();
//        Course course;
//
//        for(int i = 0; i < courses.size(); i++) {
//            course = courses.get(i);
//            rows.add(new SubjectsListAdapter.Item(course.getSubject() + course.getCatalogNumber(), course.getTitle()));
//        }
//
//        adapter.setRows(rows);
//        setListAdapter(adapter);
//    }

    public void loadCourses(String subject) {
        dialog.setMessage("Loading courses");
        dialog.show();
        networkManager.getCourses(subject, new CoursesListener() {
            @Override
            public void onSuccess(ArrayList<Course> courses) {
                if(courses.size() == 0) {
                    dialog.dismiss();
                    //TODO: Show network error
                } else {
                    setAdapter(courses);
                    ListView mListView = (ListView) findViewById(android.R.id.list);
                    mListView.setVisibility(View.VISIBLE);
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
