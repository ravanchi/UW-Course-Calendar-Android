package com.alirezatr.uwcalendar.network;

import static com.alirezatr.uwcalendar.network.RequestKeys.*;

import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.listeners.CoursesListener;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CoursesRequest {
    private CoursesListener completionHandler;
    private String url = coursesRequestUrl;

    public CoursesRequest(String subject, CoursesListener completionHandler, RequestQueue requestQueue) {
        this.url = url + subject + ".json?key=" + apiKey;
        this.completionHandler = completionHandler;
        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null, successListener(), errorListener());
        requestQueue.add(newRequest);
    }

    private static ArrayList<Course> parseResponse(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();

        ArrayList<Course> courseList = new ArrayList<Course>(dataArrayLength);
        if(dataArrayLength != 0) {
            Course courseModel;
            JSONObject courseData;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    courseData = dataArray.getJSONObject(i);
                    courseModel = new Course(courseData.getString("course_id"), courseData.getString("subject"), courseData.getString("catalog_number"),
                            courseData.getString("title"), courseData.getString("description"));
                    courseList.add(courseModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return courseList;
    }

    private Response.Listener<JSONObject> successListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray dataArray = response.getJSONArray("data");
                    completionHandler.onSuccess(parseResponse(dataArray));
                } catch (JSONException exception) {
                    completionHandler.onError(exception);
                }
            }
        };
    }

    private Response.ErrorListener errorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                completionHandler.onError(volleyError);
            }
        };
    }
}
