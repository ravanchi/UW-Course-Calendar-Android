package com.alirezatr.uwcalendar;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.TwoLineListItem;

import java.util.ArrayList;

/**
 * Created by ali on 1/20/2014.
 */
public class CoursesAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Course> courses;

    public CoursesAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Course getItem(int position) {
        return courses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TwoLineListItem twoLineListItem;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            twoLineListItem = (TwoLineListItem) inflater.inflate(android.R.layout.simple_list_item_2, null);
        }
        else {
            twoLineListItem = (TwoLineListItem) convertView;
        }

        TextView text1 = twoLineListItem.getText1();
        TextView text2 = twoLineListItem.getText2();

        //text1.setText(courses.get(position).getSubject() + courses.get(position).getCatalogNumber() + ": " + courses.get(position).getTitle());
        text1.setText(courses.get(position).getSubject() + courses.get(position).getCatalogNumber());
        text1.setTextColor(Color.BLACK);
        text2.setText(courses.get(position).getTitle());
        text2.setTextColor(Color.DKGRAY);

        return twoLineListItem;
    }
}
