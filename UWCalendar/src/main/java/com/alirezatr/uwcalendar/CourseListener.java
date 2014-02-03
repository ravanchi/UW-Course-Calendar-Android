package com.alirezatr.uwcalendar;

import java.util.ArrayList;

/**
 * Created by ali on 1/20/2014.
 */
public interface CourseListener {
    void onSuccess(Course course);
    void onError(Exception error);
}
