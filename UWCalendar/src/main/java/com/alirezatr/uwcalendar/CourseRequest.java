package com.alirezatr.uwcalendar;

import static com.alirezatr.uwcalendar.RequestKeys.*;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ali on 1/20/2014.
 */
public class CourseRequest {
    private CourseListener completionHandler;
    private String url = coursesRequestUrl;

    public CourseRequest(String subject, String catalog_number, CourseListener completionHandler, RequestQueue requestQueue) {
        this.url = url + subject + "/" + catalog_number + ".json?key=" + apiKey;
        this.completionHandler = completionHandler;
        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null, successListener(), errorListener());
        requestQueue.add(newRequest);
    }

    private static Course parseResponse(JSONObject dataObject) throws JSONException {
        Course courseModel = null;
        JSONObject courseData;
        JSONArray instructions;

        try {
            courseData = dataObject;
            List<String> instructionList = new ArrayList<String>();
            instructions = courseData.getJSONArray("instructions");
            for(int i = 0; i < instructions.length(); i++) {
                instructionList.add(instructions.getString(i));
            }
            courseModel = new Course(courseData.getString("course_id"), courseData.getString("subject"), courseData.getString("catalog_number"),
                    courseData.getString("title"), courseData.getString("description"), instructionList, courseData.getString("prerequisites"), courseData.getString("antirequisites"), courseData.getString("notes"));
        } catch(JSONException exception) {
            throw exception;
        }
        return courseModel;
    }

    private Response.Listener<JSONObject> successListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject dataObject = response.getJSONObject("data");
                    completionHandler.onSuccess(parseResponse(dataObject));
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
