package com.alirezatr.uwcalendar.listeners;

import com.alirezatr.uwcalendar.models.Subject;

import java.util.ArrayList;

public interface SubjectsListener {
    void onSuccess(ArrayList<Subject> subjects);
    void onError(Exception error);
}