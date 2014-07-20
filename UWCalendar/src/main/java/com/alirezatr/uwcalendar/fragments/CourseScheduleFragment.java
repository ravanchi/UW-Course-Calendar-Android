package com.alirezatr.uwcalendar.fragments;

import android.support.v4.app.ListFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.adapters.ClassListAdapter;
import com.alirezatr.uwcalendar.adapters.CoursesListAdapter;
import com.alirezatr.uwcalendar.models.Class;
import com.alirezatr.uwcalendar.models.ClassItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseScheduleFragment extends ListFragment {
    ListView mListView;
    private ClassListAdapter adapter = new ClassListAdapter();
    private HashMap<String, Integer> sections = new HashMap<String, Integer>();
    TextView mLoading;
    TextView mNetworkError;
    ProgressBar loadingModal;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.list_activity, container, false);
        mListView = (ListView) rootView.findViewById(android.R.id.list);
        mListView.setVisibility(View.GONE);

        mNetworkError = (TextView) rootView.findViewById(R.id.loading_fail);
        mLoading = (TextView) rootView.findViewById(R.id.loading);
        mLoading.setText("Loading Schedule");
        loadingModal = (ProgressBar) rootView.findViewById(R.id.loading_modal);
        return rootView;
    }

    public void addClassView(ArrayList<Class> classes) {
        mListView.setVisibility(View.VISIBLE);
        loadingModal.setVisibility(View.GONE);
        mLoading.setVisibility(View.GONE);
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
                    rows.add(new ClassListAdapter.Header(currentClassType));
                    sections.put(currentClassType, start);
                }

                rows.add(new ClassItem(clazz));
                previousClassType = currentClassType;
            }

            adapter.setRows(rows);
            adapter.notifyDataSetChanged();
            setListAdapter(adapter);
        } else {
            mListView.setVisibility(View.GONE);
            mNetworkError.setText("No classes found");
            mNetworkError.setVisibility(View.VISIBLE);
        }
    }

    public void showError() {
        mLoading.setVisibility(View.GONE);
        mListView.setVisibility(View.GONE);
        loadingModal.setVisibility(View.GONE);
        mNetworkError.setVisibility(View.VISIBLE);
    }
}
