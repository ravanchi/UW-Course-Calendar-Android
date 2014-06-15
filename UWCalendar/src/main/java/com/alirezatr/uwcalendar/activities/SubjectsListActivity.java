package com.alirezatr.uwcalendar.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.SubjectsAdapter;
import com.alirezatr.uwcalendar.listeners.SubjectsListener;
import com.alirezatr.uwcalendar.models.Subject;
import com.alirezatr.uwcalendar.network.NetworkManager;

import java.util.ArrayList;

public class SubjectsListActivity extends ListActivity {
    private NetworkManager networkManager;
    public ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getActionBar();
        actionBar.setTitle("Subjects");
        actionBar.setSubtitle("Waterloo Calendar");

        networkManager = new NetworkManager(this);
        dialog = new ProgressDialog(this);
        loadSubjects();
    }

    public void loadSubjects() {
        dialog.setMessage("Loading subjects");
        dialog.show();
        networkManager.getSubjects(new SubjectsListener() {
            @Override
            public void onSuccess(ArrayList<Subject> subjects) {
                setListAdapter(new SubjectsAdapter(getApplicationContext(), subjects));
                dialog.dismiss();
            }

            @Override
            public void onError(Exception error) {
                dialog.dismiss();
                Toast.makeText(getApplicationContext(), "Error loading subjects, please try again later", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Object o = this.getListAdapter().getItem(position);
        Subject subject = (Subject) o;
        Intent intent = new Intent(getListView().getContext(), CoursesListActivity.class);
        intent.putExtra("subject", subject.getSubject());
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
                loadSubjects();
                break;
            default:
                break;
        }
        return true;
    }
}
