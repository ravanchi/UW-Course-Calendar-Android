package com.alirezatr.uwcalendar.fragments;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
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

public class CourseDetailFragment extends ListFragment {
    TextView title;
    TextView description;
    TextView prerequisites;
    TextView antirequisites;
    TextView termsOfferedText;
    TextView instructionsText;
    TextView notes;
    TextView courseUrlTextView;
    TextView courseUnits;
    TextView courseOnline;

    LinearLayout courseDetails;
    LinearLayout loading;

    TextView loadingText;
    TextView networkError;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.course_details_fragment, container, false);

        courseDetails = (LinearLayout) rootView.findViewById(R.id.course_details);
        loading = (LinearLayout) rootView.findViewById(R.id.loading);

        loadingText = (TextView) rootView.findViewById(R.id.loading_text);
        networkError = (TextView) rootView.findViewById(R.id.loading_fail);
        title = (TextView) rootView.findViewById(R.id.course_title);
        courseOnline = (TextView) rootView.findViewById(R.id.course_offer_online);
        description = (TextView) rootView.findViewById(R.id.course_description);
        courseUnits = (TextView) rootView.findViewById(R.id.course_units);
        prerequisites = (TextView) rootView.findViewById(R.id.course_prerequisites);
        antirequisites = (TextView) rootView.findViewById(R.id.course_antirequisites);
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

    public void showError() {
        courseDetails.setVisibility(View.GONE);
        loading.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);
        networkError.setVisibility(View.VISIBLE);
    }

    public void populateDetailsView(Course course) {
        courseDetails.setVisibility(View.VISIBLE);
        loading.setVisibility(View.GONE);
        loadingText.setVisibility(View.GONE);
        networkError.setVisibility(View.GONE);

        if(course.getOfferings() != null && course.getOfferings().size() != 0) {
            if(course.getOfferings().get("online")){
                courseOnline.setText("Yes");
            }
        }

        if(course.getPrerequisites() != null) {
            prerequisites.setText(course.getPrerequisites());
        }

        if(course.getAntirequisites() != null) {
            antirequisites.setText(course.getAntirequisites());
        }

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
            courseUrlTextView.setText(course.getUrl());
        }

        courseUnits.setText(course.getUnits() + " units");
    }
}