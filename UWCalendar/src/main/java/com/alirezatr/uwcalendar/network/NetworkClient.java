package com.alirezatr.uwcalendar.network;

import android.content.Context;

import com.alirezatr.uwcalendar.listeners.*;
import com.alirezatr.uwcalendar.models.Course;
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

public class NetworkClient {
    private RequestQueue requestQueue;
    private Gson gson = new Gson();

    public NetworkClient(Context context) {
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public void cancelAllRequests() {
        requestQueue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public void fetchSubjectCourses(String courseSubject, final CoursesListener completionHandler) {
        final Type coursesListType = new TypeToken<ArrayList<Course>>(){}.getType();
        String url = StringUtils.generateRequestUrl(RequestType.COURSES_LIST, courseSubject);

        NetworkResponseListener requestCallback = new NetworkResponseListener() {
            @Override
            public void onSuccess(JSONObject networkResponse) {
                try {
                    JSONArray dataArray = networkResponse.getJSONArray(DATA);
                    ArrayList<Course> courses = gson.fromJson(dataArray.toString(), coursesListType);
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
                successListener(requestCallback), errorListener(requestCallback));
        requestQueue.add(newRequest);
    }

    public void fetchCourse(String courseSubject, String courseCatalogNumber, final CourseListener completionHandler) {
        final Type courseType = new TypeToken<Course>(){}.getType();
        String requestUrl = StringUtils.generateRequestUrl(RequestType.COURSE, courseSubject, courseCatalogNumber);

        NetworkResponseListener requestCallback = new NetworkResponseListener() {
            @Override
            public void onSuccess(JSONObject networkResponse) {
                try {
                    JSONObject dataObject = networkResponse.getJSONObject(DATA);
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

        JsonObjectRequest courseRequest = new JsonObjectRequest(Request.Method.GET, requestUrl, null,
                successListener(requestCallback), errorListener(requestCallback));
        requestQueue.add(courseRequest);
    }

    public void fetchCourseClass(String courseSubject, String courseCatalogNumber, final ClassesListener completionHandler) {
        String url = StringUtils.generateRequestUrl(RequestType.CLASS, courseSubject, courseCatalogNumber);

        NetworkResponseListener requestCallback = new NetworkResponseListener() {
            @Override
            public void onSuccess(JSONObject networkResponse) {
                try {
                    JSONArray dataArray = networkResponse.getJSONArray(DATA);
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

        JsonObjectRequest courseClassRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                successListener(requestCallback), errorListener(requestCallback));
        requestQueue.add(courseClassRequest);
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