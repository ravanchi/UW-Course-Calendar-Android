package com.alirezatr.uwcalendar.Network;

import android.content.Context;

import com.alirezatr.uwcalendar.Listeners.CourseClassListener;
import com.alirezatr.uwcalendar.Listeners.CourseListener;
import com.alirezatr.uwcalendar.Listeners.CoursesListener;
import com.alirezatr.uwcalendar.Listeners.SubjectsListener;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by ali on 1/20/2014.
 */
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

    public void getCourseClass(String subject, String catalog_number, CourseClassListener completionHandler) {
        CourseClassRequest request = new CourseClassRequest(subject, catalog_number, completionHandler, requestQueue);
    }

}
