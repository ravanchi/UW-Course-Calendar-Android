package com.alirezatr.uwcalendar.network;

import android.content.Context;

import com.alirezatr.uwcalendar.R;
import com.alirezatr.uwcalendar.listeners.*;
import com.alirezatr.uwcalendar.utils.LoggerUtils;
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
            LoggerUtils.LogError("getSubjects", context.getResources().getString(R.string.error_null_params), NetworkManager.class);
        }
        new SubjectsRequest(completionHandler, requestQueue);
    }

    public void getCourses(String subject, CoursesListener completionHandler) {
        if(subject.isEmpty() || completionHandler == null) {
            LoggerUtils.LogError("getCourses", context.getResources().getString(R.string.error_null_params), NetworkManager.class);
        }
        new CoursesRequest(subject, completionHandler, requestQueue);
    }

    public void getCourse(String subject, String catalog_number, CourseListener completionHandler) {
        if(subject.isEmpty() || catalog_number.isEmpty() || completionHandler == null) {
            LoggerUtils.LogError("getCourse", context.getResources().getString(R.string.error_null_params), NetworkManager.class);
        }
        new CourseRequest(subject, catalog_number, completionHandler, requestQueue);
    }

    public void getCourseClass(String subject, String catalog_number, ClassesListener completionHandler) {
        if(subject.isEmpty() || catalog_number.isEmpty() || completionHandler == null) {
            LoggerUtils.LogError("getCourseClass", context.getResources().getString(R.string.error_null_params), NetworkManager.class);
        }
        new ClassRequest(subject, catalog_number, completionHandler, requestQueue);
    }
}
