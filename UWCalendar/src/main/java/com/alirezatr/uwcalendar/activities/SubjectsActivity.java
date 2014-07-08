package com.alirezatr.uwcalendar.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.SubjectsListAdapter;
import com.alirezatr.uwcalendar.adapters.SubjectsListAdapter.Item;
import com.alirezatr.uwcalendar.models.Subject;
import com.alirezatr.uwcalendar.utils.FilterUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class SubjectsActivity extends ListActivity {
    private SubjectsListAdapter adapter = new SubjectsListAdapter();
    private List<Object[]> alphabet = new ArrayList<Object[]>();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.main_list);

        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.actionbar);
        actionBar.setTitle(getResources().getString(R.string.subjects));
        actionBar.setSubtitle(getResources().getString(R.string.app_name));
        actionBar.setDisplayHomeAsUpEnabled(false);

        ImageView view = (ImageView)findViewById(android.R.id.home);
        view.setPadding(0, 0, 10, 0);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        loadSubjects();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    public void loadSubjects() {
        final Type subjectListType = new TypeToken<ArrayList<Subject>>(){}.getType();
        TextView mLoadingError = (TextView) findViewById(R.id.loading);

        mProgressDialog.setMessage(getResources().getString(R.string.loading_subjects));
        mProgressDialog.show();
        String json = null;
        try {
            InputStream is = getAssets().open("subjects.txt");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");

            ListView mListView = (ListView) findViewById(android.R.id.list);
            mListView.setVisibility(View.VISIBLE);
            mProgressDialog.dismiss();

        } catch (IOException ex) {
            mLoadingError.setVisibility(View.VISIBLE);
            mProgressDialog.dismiss();
            ex.printStackTrace();
        }

        if(json != null) {
            ArrayList<Subject> subjectList = new Gson().fromJson(json, subjectListType);
            setAdapter(subjectList);
        }
        else {
            mLoadingError.setVisibility(View.VISIBLE);
            mProgressDialog.dismiss();
        }
    }

    private void setAdapter(ArrayList<Subject> subjects) {
        List rows = new ArrayList();
        ArrayList<String> blacklist = FilterUtils.getRestrictedSubjects();
        int start = 0;
        int end;
        String previousLetter = null;
        Object[] tmpIndexItem;
        Pattern numberPattern = Pattern.compile("[0-9]");
        Subject subject;

        for(int i = 0; i < subjects.size(); i++) {
            subject = subjects.get(i);
            if(blacklist.contains(subject.getSubject())) {
                subjects.remove(subject);
            }
            else {
                String firstLetter = subject.getSubject().substring(0, 1);

                if (numberPattern.matcher(firstLetter).matches()) {
                    firstLetter = "#";
                }

                if (previousLetter != null && !firstLetter.equals(previousLetter)) {
                    end = rows.size() - 1;
                    tmpIndexItem = new Object[3];
                    tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
                    tmpIndexItem[1] = start;
                    tmpIndexItem[2] = end;
                    alphabet.add(tmpIndexItem);

                    start = end + 1;
                }

                if (!firstLetter.equals(previousLetter)) {
                    rows.add(new SubjectsListAdapter.Section(firstLetter));
                    sections.put(firstLetter, start);
                }

                rows.add(new Item(subject));
                previousLetter = firstLetter;
            }
        }

        if(previousLetter != null) {
            tmpIndexItem = new Object[3];
            tmpIndexItem[0] = previousLetter.toUpperCase(Locale.UK);
            tmpIndexItem[1] = start;
            tmpIndexItem[2] = rows.size() - 1;
            alphabet.add(tmpIndexItem);
        }

        adapter.setRows(rows);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        SubjectsListAdapter.Item rowItem = (SubjectsListAdapter.Item) this.getListAdapter().getItem(position);
        Intent intent = new Intent(getListView().getContext(), CoursesActivity.class);
        intent.putExtra("subject", rowItem.subject.getSubject());
        getListView().getContext().startActivity(intent);
    }
}
