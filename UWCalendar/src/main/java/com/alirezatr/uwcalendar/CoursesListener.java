package com.alirezatr.uwcalendar;

import java.util.ArrayList;

/**
 * Created by ali on 1/20/2014.
 */
public interface CoursesListener {
    void onSuccess(ArrayList<Course> courses);
    void onError(Exception error);
}
