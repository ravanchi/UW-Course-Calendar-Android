package com.alirezatr.uwcalendar.network;

import android.content.Context;

import com.alirezatr.uwcalendar.listeners.*;
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
        new SubjectsRequest(completionHandler, requestQueue);
    }

    public void getCourses(String subject, CoursesListener completionHandler) {
        new CoursesRequest(subject, completionHandler, requestQueue);
    }

    public void getCourse(String subject, String catalog_number, CourseListener completionHandler) {
        new CourseRequest(subject, catalog_number, completionHandler, requestQueue);
    }

    public void getCourseClass(String subject, String catalog_number, ClassesListener completionHandler) {
        new ClassRequest(subject, catalog_number, completionHandler, requestQueue);
    }
}
