package com.alirezatr.uwcalendar.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.models.Course;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.lang.reflect.Type;

public class CourseDetailFragment extends Fragment {
    TextView title;
    TextView description;
    TextView prerequisitesTitle;
    TextView prerequisites;
    TextView antirequisites;
    TextView antirequisitesTitle;
    TextView termsOfferedText;
    TextView instructionsTitle;
    TextView instructionsText;
    TextView notes;
    TextView courseUrlTextView;
    TextView courseUnits;

    LinearLayout termsOffered;
    LinearLayout courseUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.course_details_fragment, container, false);


        courseUrl = (LinearLayout) rootView.findViewById(R.id.course_url);
        title = (TextView) rootView.findViewById(R.id.course_title);
        description = (TextView) rootView.findViewById(R.id.course_description);
        courseUnits = (TextView) rootView.findViewById(R.id.course_units);
        prerequisitesTitle = (TextView) rootView.findViewById(R.id.course_prerequisites_title);
        prerequisites = (TextView) rootView.findViewById(R.id.course_prerequisites);
        antirequisites = (TextView) rootView.findViewById(R.id.course_antirequisites);
        antirequisitesTitle = (TextView) rootView.findViewById(R.id.course_antirequisites_title);
        termsOfferedText = (TextView) rootView.findViewById(R.id.course_terms_textView);
        instructionsText = (TextView) rootView.findViewById(R.id.course_instructions);
        courseUrlTextView = (TextView) rootView.findViewById(R.id.course_url_textView);
        notes = (TextView) rootView.findViewById(R.id.course_notes);

        Bundle bundle = this.getArguments();
        final Type courseType = new TypeToken<Course>(){}.getType();
        Course course = new Gson().fromJson(bundle.getString("course"), courseType);
        if(course != null) {
            setView(course);
        }
        return rootView;
    }

    public void setView(Course course) {
        if(course.getTitle() != null) {
            title.setText(course.getTitle());
        }

        if(course.getDescription() != null) {
            description.setText(course.getDescription());
        }
    }

    public void populateView(Course course) {
        prerequisitesTitle.setVisibility(View.VISIBLE);
        String prerequisiteText = (course.getPrerequisites()==null)? "none" : course.getPrerequisites();
        prerequisites.setText(prerequisiteText);

        antirequisitesTitle.setVisibility(View.VISIBLE);
        String antirequisiteText = (course.getAntirequisites()==null)? "none" : course.getAntirequisites();
        antirequisites.setText(antirequisiteText);

        if(course.getNotes() != null) {
            notes.setVisibility(View.VISIBLE);
            notes.setText(course.getNotes());
        }

        if(course.getTermsOffered() != null && course.getTermsOffered().size() != 0) {
            String termsOfferedString = TextUtils.join(", ", course.getTermsOffered());
            termsOfferedText.setText(termsOfferedString);
        }

        if(course.getInstructions() != null && course.getInstructions().size() != 0) {
            String componentsString = TextUtils.join(", ", course.getInstructions());
            instructionsText.setText(componentsString);
        }

        if(course.getUrl() != null) {
            courseUrl.setVisibility(View.VISIBLE);
            courseUrlTextView.setText(course.getUrl());
        }

        courseUnits.setText(course.getUnits() + " units");
    }
}