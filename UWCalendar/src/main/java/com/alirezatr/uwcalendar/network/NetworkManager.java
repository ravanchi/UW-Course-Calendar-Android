package com.alirezatr.uwcalendar.network;

import android.content.Context;

import com.alirezatr.uwcalendar.listeners.*;
import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.models.Subject;
import com.alirezatr.uwcalendar.utils.NetworkUtils;
import com.alirezatr.uwcalendar.utils.StringUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

import static com.alirezatr.uwcalendar.network.RequestKeys.DATA;

public class NetworkManager {
    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    public NetworkManager(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void getSubjects(final SubjectsListener completionHandler) {
        final Type subjectListType = new TypeToken<ArrayList<Subject>>(){}.getType();
        String url = StringUtils.generateUrl(RequestTypes.SUBJECTS_LIST);

        NetworkResponseListener callback = new NetworkResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray(DATA);
                    String data = dataArray.toString();
                    ArrayList<Subject> subjectList = gson.fromJson(dataArray.toString(), subjectListType);
                    completionHandler.onSuccess(subjectList);
                } catch (JSONException exception) {
                    onError(exception);
                }
            }
            @Override
            public void onError(Exception exception) {
                completionHandler.onError(exception);
            }
        };

        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener(callback), errorListener(callback));
        requestQueue.add(newRequest);
    }

    public void getCourses(String subject, final CoursesListener completionHandler) {
        final Type coursesListType = new TypeToken<ArrayList<Course>>(){}.getType();
        String url = StringUtils.generateUrl(RequestTypes.COURSES_LIST, subject);

        NetworkResponseListener callback = new NetworkResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray(DATA);
                    ArrayList<Course> coursesList = gson.fromJson(dataArray.toString(), coursesListType);
                    completionHandler.onSuccess(coursesList);
                } catch (JSONException exception) {
                    onError(exception);
                }
            }
            @Override
            public void onError(Exception exception) {
                completionHandler.onError(exception);
            }
        };

        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener(callback), errorListener(callback));
        requestQueue.add(newRequest);
    }

    public void getCourse(String subject, String catalog_number, final CourseListener completionHandler) {
        final Type courseType = new TypeToken<Course>(){}.getType();
        String url = StringUtils.generateUrl(RequestTypes.COURSE, subject, catalog_number);

        NetworkResponseListener callback = new NetworkResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONObject dataObject = response.getJSONObject(DATA);
                    Course courses = gson.fromJson(dataObject.toString(), courseType);
                    completionHandler.onSuccess(courses);
                } catch (JSONException exception) {
                    onError(exception);
                }
            }
            @Override
            public void onError(Exception exception) {
                completionHandler.onError(exception);
            }
        };

        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener(callback), errorListener(callback));
        requestQueue.add(newRequest);
    }

    public void getCourseClass(String subject, String catalog_number, final ClassesListener completionHandler) {
        String url = StringUtils.generateUrl(RequestTypes.CLASS, subject, catalog_number);

        NetworkResponseListener callback = new NetworkResponseListener() {
            @Override
            public void onSuccess(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray(DATA);
                    completionHandler.onSuccess(NetworkUtils.parseClassList(dataArray));
                } catch (JSONException exception) {
                    onError(exception);
                }
            }
            @Override
            public void onError(Exception exception) {
                completionHandler.onError(exception);
            }
        };

        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener(callback), errorListener(callback));
        requestQueue.add(newRequest);
    }

    private Response.Listener<JSONObject> successListener(final NetworkResponseListener callback) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                callback.onSuccess(response);
            }
        };
    }

    private Response.ErrorListener errorListener(final NetworkResponseListener callback) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                callback.onError(volleyError);
            }
        };
    }
}
