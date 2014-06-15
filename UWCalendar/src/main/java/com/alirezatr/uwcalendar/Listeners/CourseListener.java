package com.alirezatr.uwcalendar.listeners;

import com.alirezatr.uwcalendar.models.Course;

public interface CourseListener {
    void onSuccess(Course course);
    void onError(Exception error);
}