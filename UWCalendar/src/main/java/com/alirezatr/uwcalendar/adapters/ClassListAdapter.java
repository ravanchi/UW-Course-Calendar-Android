package com.alirezatr.uwcalendar.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.models.Class;
import com.alirezatr.uwcalendar.models.ClassItem;
import com.alirezatr.uwcalendar.models.Row;
import com.alirezatr.uwcalendar.views.PinnedSectionListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClassListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private List rows;

    @Override
    public int getCount() {
        return rows.size();
    }

    @Override
    public Object getItem(int i) {
        return rows.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        View view  = convertView;

        if(getItemViewType(i) == 0) {
            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.class_row_item, null, false);
            }

            ClassItem item = (ClassItem) getItem(i);

            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            SimpleDateFormat of = new SimpleDateFormat("h:mm");
            Date startTime = new Date();
            Date endTime = new Date();
            try {
                startTime = df.parse(item.clazz.getStartTime());
                endTime = df.parse(item.clazz.getEndTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String time;
            if(item.clazz.getWeekdays() != null && !item.clazz.getWeekdays().contains("null")) {
                time = of.format(startTime) + " - " + of.format(endTime) + " " + item.clazz.getWeekdays();
            } else {
                time = of.format(startTime) + " - " + of.format(endTime);
            }


            String room = item.clazz.getLocation() + item.clazz.getRoom();
            String instructor = item.clazz.getInstructor();
            if(instructor != null || !instructor.contains("null")) {
                int commaIndex = instructor.indexOf(",");
                if(commaIndex != -1) {
                    instructor = instructor.substring(commaIndex + 1) + " " + instructor.substring(0, commaIndex);
                }

            } else {
                instructor = "";
            }

            TextView textView = (TextView) view.findViewById(R.id.class_lecture);
            textView.setText(item.clazz.getSection());

            TextView textView2 = (TextView) view.findViewById(R.id.class_instructor);

            if(instructor == "") {
                textView2.setVisibility(view.GONE);
            } else {
                textView2.setText(instructor);
            }

            TextView textView3 = (TextView) view.findViewById(R.id.class_time);
            textView3.setText(time);

            TextView textView4 = (TextView) view.findViewById(R.id.class_room);
            if(room.contains("null")) {
                textView4.setVisibility(View.GONE);
            } else {
                textView4.setText(room);
            }
        }
        else {
            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_section, null, false);
            }

            Header section = (Header) getItem(i);
            TextView textView = (TextView) view.findViewById(R.id.section_title);
            textView.setText(section.text);
        }

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if(getItem(position) instanceof Header) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    public static final class Header extends Row {
        public final String text;

        public Header(String text) {
            this.text = text;
        }
    }
}
