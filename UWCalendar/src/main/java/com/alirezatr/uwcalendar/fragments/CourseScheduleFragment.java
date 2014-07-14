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

import java.util.ArrayList;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardArrayAdapter;
import it.gmariotti.cardslib.library.internal.CardExpand;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.ViewToClickToExpand;
import it.gmariotti.cardslib.library.view.CardListView;
import it.gmariotti.cardslib.library.view.CardView;

public class CourseScheduleFragment extends Fragment {
    View rootView;
    LinearLayout layout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.course_schedule_fragment, container, false);
        //layout = (LinearLayout) rootView.findViewById(R.id.linlayout);
        return rootView;
    }

//    public void addClassView(Class courseClass) {
//        if (courseClass.getInstructor() != null && getActivity() != null) {
//            TextView tv = new TextView(getActivity());
//            tv.setPadding(30, 5, 30, 0);
//            tv.setTextColor(Color.DKGRAY);
//            if(courseClass.getLocation() == null){
//                courseClass.setLocation("");
//            }
//            if(courseClass.getRoom() == null) {
//                courseClass.setRoom("");
//            }
//            tv.setText(courseClass.getSection() + " " + courseClass.getInstructor() + "\n" + courseClass.getWeekdays() + " " +
//                    courseClass.getStartTime() + " - " + courseClass.getEndTime() + " " + courseClass.getLocation()+courseClass.getRoom());
//            layout.addView(tv);
//        }
//    }

    public void addClassView(ArrayList<Card> cards) {
        if(getActivity() != null) {
            CardArrayAdapter mCardArrayAdapter = new CardArrayAdapter(getActivity(), cards);
            CardListView listView = (CardListView) rootView.findViewById(R.id.list_cardId);
            if (listView != null) {
                listView.setAdapter(mCardArrayAdapter);
            }
        }
    }
}
