package com.alirezatr.uwcalendar.network;

import android.content.Context;

import com.alirezatr.uwcalendar.listeners.ClassesListener;
import com.alirezatr.uwcalendar.listeners.CourseListener;
import com.alirezatr.uwcalendar.listeners.CoursesListener;
import com.alirezatr.uwcalendar.listeners.SubjectsListener;
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
        SubjectsRequest request = new SubjectsRequest(completionHandler, requestQueue);
    }

    public void getCourses(String subject, CoursesListener completionHandler) {
        CoursesRequest request = new CoursesRequest(subject, completionHandler, requestQueue);
    }

    public void getCourse(String subject, String catalog_number, CourseListener completionHandler) {
        CourseRequest request = new CourseRequest(subject, catalog_number, completionHandler, requestQueue);
    }

    public void getCourseClass(String subject, String catalog_number, ClassesListener completionHandler) {
        ClassRequest request = new ClassRequest(subject, catalog_number, completionHandler, requestQueue);
    }
}
