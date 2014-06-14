package com.alirezatr.uwcalendar.Listeners;

import com.alirezatr.uwcalendar.Models.CourseClass;

import java.util.ArrayList;

/**
 * Created by ali on 1/20/2014.
 */
public interface CourseClassListener {
    void onSuccess(ArrayList<CourseClass> courseClass);
    void onError(Exception error);
}
