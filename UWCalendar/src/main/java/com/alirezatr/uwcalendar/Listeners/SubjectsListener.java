package com.alirezatr.uwcalendar.Listeners;

import com.alirezatr.uwcalendar.Models.Subject;

import java.util.ArrayList;

/**
 * Created by ali on 1/20/2014.
 */
public interface SubjectsListener {
    void onSuccess(ArrayList<Subject> subjects);
    void onError(Exception error);
}
