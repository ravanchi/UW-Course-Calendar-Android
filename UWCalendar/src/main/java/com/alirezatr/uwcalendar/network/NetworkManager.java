package com.alirezatr.uwcalendar.network;

import android.content.Context;

import com.alirezatr.uwcalendar.listeners.ClassesListener;
import com.alirezatr.uwcalendar.listeners.CourseListener;
import com.alirezatr.uwcalendar.listeners.CoursesListener;
import com.alirezatr.uwcalendar.listeners.SubjectsListener;
import com.alirezatr.uwcalendar.utils.LoggerUtil;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class NetworkManager {
    private Context context;
    private RequestQueue requestQueue;

    public NetworkManager(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void getSubjects(SubjectsListener completionHandler) {
        if(completionHandler == null) {
            LoggerUtil.LogError("getSubjects", "empty or null parameter", NetworkManager.class);
        }
        new SubjectsRequest(completionHandler, requestQueue);
    }

    public void getCourses(String subject, CoursesListener completionHandler) {
        if(subject.isEmpty() || completionHandler == null) {
            LoggerUtil.LogError("getCourses", "empty or null parameter", NetworkManager.class);
        }
        new CoursesRequest(subject, completionHandler, requestQueue);
    }

    public void getCourse(String subject, String catalog_number, CourseListener completionHandler) {
        if(!subject.isEmpty() && !catalog_number.isEmpty() && completionHandler != null) {
            new CourseRequest(subject, catalog_number, completionHandler, requestQueue);
        }
    }

    public void getCourseClass(String subject, String catalog_number, ClassesListener completionHandler) {
        if(!subject.isEmpty() && !catalog_number.isEmpty() && completionHandler != null) {
            new ClassRequest(subject, catalog_number, completionHandler, requestQueue);
        }
    }
}
