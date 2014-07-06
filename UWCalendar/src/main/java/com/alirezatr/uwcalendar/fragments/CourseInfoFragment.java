package com.alirezatr.uwcalendar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.models.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

public class CourseInfoFragment extends Fragment {
    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.course_information_fragment, container, false);
        Bundle bundle = this.getArguments();
        final Type courseType = new TypeToken<Course>(){}.getType();
        Course course = new Gson().fromJson(bundle.getString("course"), courseType);
        if(course != null) {
            setView(course);
        }
        return rootView;
    }

    public void setView(Course course) {
        TextView description = (TextView) rootView.findViewById(R.id.course_description);
        TextView instructions = (TextView) rootView.findViewById(R.id.course_instructions);
        TextView prerequisitesTitle = (TextView) rootView.findViewById(R.id.course_prerequisites_title);
        TextView prerequisites = (TextView) rootView.findViewById(R.id.course_prerequisites);
        TextView antirequisites = (TextView) rootView.findViewById(R.id.course_antirequisites);
        TextView antirequisitesTitle = (TextView) rootView.findViewById(R.id.course_antirequisites_title);

        prerequisitesTitle.setText("Prerequisites");

        if(course.getDescription() != null) {
            description.setText(course.getDescription());
        }
        if(course.getInstructions() != null) {
            instructions.setText(course.getInstructions().toString());
        }
        if(course.getNotes() != null) {
            TextView notes = (TextView) rootView.findViewById(R.id.course_notes);
            notes.setText(course.getNotes());
        }

        String prerequisiteText = (course.getPrerequisites()==null)? "none" : course.getPrerequisites();
        prerequisites.setText(prerequisiteText);

        antirequisitesTitle.setText("Antirequisites");

        String antirequisiteText = (course.getAntirequisites()==null)? "none" : course.getAntirequisites();
        antirequisites.setText(antirequisiteText);
    }
}