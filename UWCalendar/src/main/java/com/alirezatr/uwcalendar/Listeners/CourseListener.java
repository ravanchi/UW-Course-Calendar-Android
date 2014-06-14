package com.alirezatr.uwcalendar.Listeners;

import com.alirezatr.uwcalendar.Models.Course;

/**
 * Created by ali on 1/20/2014.
 */
public interface CourseListener {
    void onSuccess(Course course);
    void onError(Exception error);
}
