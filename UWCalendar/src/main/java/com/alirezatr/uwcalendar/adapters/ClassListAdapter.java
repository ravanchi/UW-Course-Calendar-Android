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

    private TextView classLectureTextView;
    private TextView classNumberTextView;
    private TextView classInstructorTextView;
    private TextView classTimeTextView;
    private TextView classEnrollmentTextView;
    private TextView classRoomTextView;
    private TextView sectionTitleTextView;

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

            ListItem course = (ListItem) getItem(i);

            Drawable enrollmentIndicator;
            if (course.courseClass.getEnrollmentTotal() >= course.courseClass.getEnrollmentCapacity()) {
                enrollmentIndicator = context.getResources().getDrawable(R.drawable.class_enrollment_idicator_closed);
            } else if (course.courseClass.getEnrollmentTotal() < course.courseClass.getEnrollmentCapacity()) {
                enrollmentIndicator = context.getResources().getDrawable(R.drawable.class_enrollment_idicator_open);
            } else {
                enrollmentIndicator = context.getResources().getDrawable(R.drawable.class_enrollment_idicator_default);
            }
            enrollmentView.setBackgroundDrawable(enrollmentIndicator);

            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            SimpleDateFormat of = new SimpleDateFormat("h:mm");
            Date startTime = new Date();
            Date endTime = new Date();
            try {
                startTime = df.parse(course.courseClass.getStartTime());
                endTime = df.parse(course.courseClass.getEndTime());
            } catch (Exception e) {
                e.printStackTrace();
            }

            String time;
            if(course.courseClass.getWeekdays() != null && !course.courseClass.getWeekdays().contains("null")) {
                time = of.format(startTime) + " - " + of.format(endTime) + " " + course.courseClass.getWeekdays();
            } else {
                time = of.format(startTime) + " - " + of.format(endTime);
            }


            String room = course.courseClass.getLocation() + course.courseClass.getRoom();
            String instructor = course.courseClass.getInstructor();
            if(instructor != null || !instructor.contains("null")) {
                int commaIndex = instructor.indexOf(",");
                if(commaIndex != -1) {
                    instructor = instructor.substring(commaIndex + 1) + " " + instructor.substring(0, commaIndex);
                }
            }
            classLectureTextView = (TextView) view.findViewById(R.id.class_lecture);
            classLectureTextView.setText(course.courseClass.getSection());

            classNumberTextView = (TextView) view.findViewById(R.id.class_number);
            classNumberTextView.setText("(" + course.courseClass.getClassNumber() + ")");

            classInstructorTextView = (TextView) view.findViewById(R.id.class_instructor);
            classInstructorTextView.setText(instructor);

            classTimeTextView = (TextView) view.findViewById(R.id.class_time);
            classTimeTextView.setText(time);

            classEnrollmentTextView = (TextView) view.findViewById(R.id.class_enrollment);
            classEnrollmentTextView.setText("Capacity: (" + course.courseClass.getEnrollmentTotal() + "/" + course.courseClass.getEnrollmentCapacity() + ")");

            classRoomTextView = (TextView) view.findViewById(R.id.class_room);

            //TODO: Clean up code
            if(room.contains("null")) {
                if(course.courseClass.getSection().contains("TST") && course.courseClass.getStartDate() != "null") {
                    String month = course.courseClass.getStartDate().substring(0,2);
                    classRoomTextView.setText(months[Integer.parseInt(month) - 1] + " " + course.courseClass.getStartDate().substring(3));
                } else {
                    classRoomTextView.setVisibility(View.GONE);
                }
            } else {
                classRoomTextView.setText(room);
            }
        }
        else {
            if(view == null) {
                LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService
                        (Context.LAYOUT_INFLATER_SERVICE);
                view = (LinearLayout) inflater.inflate(R.layout.row_section, null, false);
            }

            ListHeader section = (ListHeader) getItem(i);
            sectionTitleTextView = (TextView) view.findViewById(R.id.section_title);
            sectionTitleTextView.setText(section.text);
        }

        return view;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        //TODO: Make enums
        if(getItem(position) instanceof ListHeader) {
            return 1;
        }
        else {
            return 0;
        }
    }

    @Override
    public boolean isEnabled(int position) {
        return false;
    }

    @Override
    public boolean isItemViewTypePinned(int viewType) {
        //TODO: Use enum
        return viewType == 1;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
