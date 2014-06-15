package com.alirezatr.uwcalendar.listeners;

import com.alirezatr.uwcalendar.models.Class;

import java.util.ArrayList;

public interface ClassesListener {
    void onSuccess(ArrayList<Class> classes);
    void onError(Exception error);
}