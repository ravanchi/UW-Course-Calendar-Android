package com.alirezatr.uwcalendar.fragments;

import android.support.v4.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.ClassListAdapter;
import com.alirezatr.uwcalendar.models.Class;
import com.alirezatr.uwcalendar.models.ListHeader;
import com.alirezatr.uwcalendar.models.ListItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseScheduleFragment extends ListFragment {
    ListView mListView;
    private ClassListAdapter adapter;
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    TextView mLoadingTextView;
    TextView mLoadingErrorTextView;
    ProgressBar mProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_activity, container, false);
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        mListView.setVisibility(View.GONE);

        adapter = new ClassListAdapter(getActivity());

        mLoadingErrorTextView = (TextView) rootView.findViewById(R.id.list_load_fail_text);
        mLoadingTextView = (TextView) rootView.findViewById(R.id.list_loading_text);
        mLoadingTextView.setText("Loading Schedule");
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.list_progress_bar);
        return rootView;
    }

    public void populateScheduleView(ArrayList<Class> classes) {
        mListView.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        mLoadingTextView.setVisibility(View.GONE);
        if(classes.size() > 0) {
            List rows = new ArrayList();
            int start = 0;
            int end;
            String currentClassType;
            String previousClassType = null;
            String classType;
            Class clazz;

            for (int i = 0; i < classes.size(); i++) {
                clazz = classes.get(i);
                classType = clazz.getSection();

                if (classType.contains("LEC")) {
                    currentClassType = "Lectures";
                } else if (classType.contains("TUT")) {
                    currentClassType = "Tutorials";
                } else if (classType.contains("LAB")) {
                    currentClassType = "Labs";
                } else if (classType.contains("TST")) {
                    currentClassType = "Tests";
                } else {
                    currentClassType = "Other";
                }

                if (previousClassType != null && !currentClassType.equals(previousClassType)) {
                    end = rows.size() - 1;
                    start = end + 1;
                }

                if (!currentClassType.equals(previousClassType)) {
                    rows.add(new ListHeader(currentClassType));
                    sections.put(currentClassType, start);
                }

                rows.add(new ListItem(clazz));
                previousClassType = currentClassType;
            }

            adapter.setRows(rows);
            adapter.notifyDataSetChanged();
            setListAdapter(adapter);
        } else {
            mListView.setVisibility(View.GONE);
            mLoadingErrorTextView.setText("No classes found");
            mLoadingErrorTextView.setVisibility(View.VISIBLE);
        }
    }

    public void showError() {
        mLoadingTextView.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);
        mLoadingErrorTextView.setVisibility(View.VISIBLE);
    }

    public void showLoading() {
        mLoadingTextView.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.VISIBLE);
        mLoadingErrorTextView.setVisibility(View.GONE);
    }
}
