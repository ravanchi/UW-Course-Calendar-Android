package com.alirezatr.uwcalendar.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.SubjectsListAdapter;
import com.alirezatr.uwcalendar.models.ListHeader;
import com.alirezatr.uwcalendar.models.ListItem;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.list_activity);

        ActionBar actionBar = getActionBar();
        actionBar.setIcon(R.drawable.actionbar);
        actionBar.setTitle(getResources().getString(R.string.subjects));
        actionBar.setSubtitle(getResources().getString(R.string.app_name));
        actionBar.setDisplayHomeAsUpEnabled(false);

        ImageView view = (ImageView)findViewById(android.R.id.home);
        view.setPadding(0, 0, 10, 0);

        loadSubjects();
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
    }

    public void loadSubjects() {
        final Type subjectListType = new TypeToken<ArrayList<Subject>>(){}.getType();
        TextView mLoading = (TextView) findViewById(R.id.list_loading_text);
        ProgressBar loadingModal = (ProgressBar) findViewById(R.id.list_progress_bar);
        TextView mLoadingError = (TextView) findViewById(R.id.list_load_fail_text);

        mLoading.setText("Loading Subjects");
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
            mLoading.setVisibility(View.GONE);
            loadingModal.setVisibility(View.GONE);

        } catch (IOException ex) {
            mLoading.setVisibility(View.GONE);
            loadingModal.setVisibility(View.GONE);
            mLoadingError.setVisibility(View.VISIBLE);
            ex.printStackTrace();
        }

        if(json != null) {
            ArrayList<Subject> subjectList = new Gson().fromJson(json, subjectListType);
            setAdapter(subjectList);
        }
        else {
            mLoading.setVisibility(View.GONE);
            loadingModal.setVisibility(View.GONE);
            mLoadingError.setVisibility(View.VISIBLE);
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
                    rows.add(new ListHeader(firstLetter));
                    sections.put(firstLetter, start);
                }

                rows.add(new ListItem(subject));
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
        ListItem rowItem = (ListItem) this.getListAdapter().getItem(position);
        Intent intent = new Intent(getListView().getContext(), CoursesActivity.class);
        intent.putExtra("subject", rowItem.subject.getSubject());
        getListView().getContext().startActivity(intent);
    }
}
