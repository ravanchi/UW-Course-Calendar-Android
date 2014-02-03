package com.alirezatr.uwcalendar;

import java.util.ArrayList;

/**
 * Created by ali on 1/20/2014.
 */
public interface CourseClassListener {
    void onSuccess(ArrayList<CourseClass> courseClass);
    void onError(Exception error);
}
