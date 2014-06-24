package com.alirezatr.uwcalendar.listeners;

import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.models.Subject;

import java.util.ArrayList;

public abstract class BaseListener {
    public abstract void onSubjectsSuccess(ArrayList<Subject> subjects);
    public abstract void onCoursesSuccess(ArrayList<Course> courses);
    public abstract void onError(Exception error);
}
