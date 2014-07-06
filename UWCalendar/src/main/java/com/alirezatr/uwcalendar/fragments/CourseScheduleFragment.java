package com.alirezatr.uwcalendar.fragments;

import android.content.Context;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.course_schedule_fragment, container, false);
        return rootView;
    }

    public void setView(Class courseClass, Context context, LinearLayout layout) {
        if (courseClass.getRoom() != null || courseClass.getInstructor() != null || courseClass.getLocation() != null) {
            LinearLayout.LayoutParams lparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(context);
            tv.setPadding(30, 5, 30, 0);
            tv.setTextColor(Color.DKGRAY);
            if(courseClass.getLocation() == "null"){
                courseClass.setLocation("");
            }
            if(courseClass.getRoom() == "null") {
                courseClass.setRoom("");
            }
            tv.setText(courseClass.getSection() + " " + courseClass.getInstructor() + "\n" + courseClass.getWeekdays() + " " +
                    courseClass.getStartTime() + " - " + courseClass.getEndTime() + " " + courseClass.getLocation()+courseClass.getRoom());
            layout.addView(tv);
        }
    }
}
