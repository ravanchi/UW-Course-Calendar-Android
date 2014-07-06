package com.alirezatr.uwcalendar.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.models.Class;

public class CourseScheduleFragment extends Fragment {
    LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.course_schedule_fragment, container, false);
        layout = (LinearLayout) rootView.findViewById(R.id.linlayout);
        return rootView;
    }

    public void addClassView(Class courseClass) {
        if (courseClass.getInstructor() != null && getActivity() != null) {
            TextView tv = new TextView(getActivity());
            tv.setPadding(30, 5, 30, 0);
            tv.setTextColor(Color.DKGRAY);
            if(courseClass.getLocation() == null){
                courseClass.setLocation("");
            }
            if(courseClass.getRoom() == null) {
                courseClass.setRoom("");
            }
            tv.setText(courseClass.getSection() + " " + courseClass.getInstructor() + "\n" + courseClass.getWeekdays() + " " +
                    courseClass.getStartTime() + " - " + courseClass.getEndTime() + " " + courseClass.getLocation()+courseClass.getRoom());
            layout.addView(tv);
        }
    }
}
