package com.alirezatr.uwcalendar.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.models.ListHeader;
import com.alirezatr.uwcalendar.models.ListItem;
import com.alirezatr.uwcalendar.views.PinnedSectionListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ClassListAdapter extends BaseAdapter implements PinnedSectionListView.PinnedSectionListAdapter {
    private List rows;
    private Context context;

    String[] months = {"January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"};

    public ClassListAdapter(Context context) {
        this.context = context;
    }

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

            View enrollmentView = (View) view.findViewById(R.id.class_enrollment_indicator);

            ListItem item = (ListItem) getItem(i);

            if(!item.clazz.getSection().contains("TST")) {
                Drawable enrollmentIndicator;
                if (item.clazz.getEnrollmentTotal() >= item.clazz.getEnrollmentCapacity()) {
                    enrollmentIndicator = context.getResources().getDrawable(R.drawable.class_enrollment_idicator_closed);
                } else if (item.clazz.getEnrollmentTotal() < item.clazz.getEnrollmentCapacity()) {
                    enrollmentIndicator = context.getResources().getDrawable(R.drawable.class_enrollment_idicator_open);
                } else {
                    enrollmentIndicator = context.getResources().getDrawable(R.drawable.class_enrollment_idicator_default);
                }
                enrollmentView.setBackgroundDrawable(enrollmentIndicator);
            }
            else {
                enrollmentView.setVisibility(View.GONE);
            }

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
            }
            TextView textView = (TextView) view.findViewById(R.id.class_lecture);
            textView.setText(item.clazz.getSection());

            TextView classNumber = (TextView) view.findViewById(R.id.class_number);
            classNumber.setText("(" + item.clazz.getClassNumber() + ")");

            TextView textView2 = (TextView) view.findViewById(R.id.class_instructor);

            if(instructor.contains("null") || instructor.isEmpty()) {
                    textView2.setVisibility(view.GONE);
            } else {
                textView2.setText(instructor);
            }

            TextView textView3 = (TextView) view.findViewById(R.id.class_time);
            textView3.setText(time);

            TextView textView4 = (TextView) view.findViewById(R.id.class_room);
            if(room.contains("null")) {
                if(item.clazz.getSection().contains("TST") && item.clazz.getStartDate() != "null") {
                    String month = item.clazz.getStartDate().substring(0,2);
                    textView4.setText(months[Integer.parseInt(month) - 1] + " " + item.clazz.getStartDate().substring(3));
                } else {
                    textView4.setVisibility(View.GONE);
                }
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

            ListHeader section = (ListHeader) getItem(i);
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
        if(getItem(position) instanceof ListHeader) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean isEnabled(int position) {
//        if(getItem(position) instanceof ListHeader) {
//            return false;
//        }
//        else {
//            return true;
//        }
        return false;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        return viewType == 1;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
