package com.alirezatr.uwcalendar.listeners;

import com.alirezatr.uwcalendar.models.Course;

import java.util.ArrayList;

public interface CoursesListener {
    void onSuccess(ArrayList<Course> courses);
    void onError(Exception error);
}
