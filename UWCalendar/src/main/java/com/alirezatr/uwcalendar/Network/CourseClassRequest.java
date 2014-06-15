package com.alirezatr.uwcalendar.network;

import static com.alirezatr.uwcalendar.network.RequestKeys.apiKey;

import com.alirezatr.uwcalendar.listeners.ClassesListener;
import com.alirezatr.uwcalendar.models.Class;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CourseClassRequest {
    private ClassesListener completionHandler;
    private String url;

    public CourseClassRequest(String subject, String catalog_number, ClassesListener completionHandler, RequestQueue requestQueue) {
        this.completionHandler = completionHandler;
        //this.url = coursesRequestUrl + subject + "/" + catalog_number + "/schedule.json?key=" + apiKey;
        this.url = "https://api.uwaterloo.ca/v2/terms/1139/" + subject + "/" + catalog_number + "/schedule.json?key=" + apiKey;
        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null, successListener(), errorListener());
        requestQueue.add(newRequest);
    }

    private static ArrayList<Class> parseResponse(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();

        ArrayList<Class> classList = new ArrayList<Class>(dataArrayLength);
        if(dataArrayLength != 0) {
            Class classModel;
            JSONArray classData;
            JSONObject courseData;

            String start_time;
            String end_time;
            String weekdays;

            String building;
            String rooms;
            String instructor;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    courseData = dataArray.getJSONObject(i);
                    classData = courseData.getJSONArray("classes");

                    building = classData.getJSONObject(0).getJSONObject("location").getString("building");
                    rooms = classData.getJSONObject(0).getJSONObject("location").getString("room");
                    instructor = classData.getJSONObject(0).getJSONArray("instructors").optString(0);
                    start_time = classData.getJSONObject(0).getJSONObject("date").getString("start_time");
                    end_time = classData.getJSONObject(0).getJSONObject("date").getString("end_time");
                    weekdays = classData.getJSONObject(0).getJSONObject("date").getString("weekdays");

                    classModel = new Class(courseData.getString("section"), courseData.getInt("enrollment_capacity"), courseData.getInt("enrollment_total"),
                            start_time, end_time, weekdays, building, rooms, instructor);
                    classList.add(classModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return classList;
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
