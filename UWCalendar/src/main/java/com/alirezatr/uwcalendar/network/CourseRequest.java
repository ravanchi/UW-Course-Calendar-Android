package com.alirezatr.uwcalendar.network;

import static com.alirezatr.uwcalendar.network.RequestKeys.DATA;

import com.alirezatr.uwcalendar.listeners.CourseListener;
import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.utils.StringUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

public class CourseRequest {
    private CourseListener completionHandler;
    private Gson gson = new Gson();
    Type courseType = new TypeToken<Course>(){}.getType();

    public CourseRequest(String subject, String catalog_number, CourseListener completionHandler, RequestQueue requestQueue) {
        String url = StringUtils.generateUrl(RequestType.COURSE, subject, catalog_number);
        this.completionHandler = completionHandler;
        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null, successListener(), errorListener());
        requestQueue.add(newRequest);
        requestQueue.start();
    }

    private Response.Listener<JSONObject> successListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
            try {
                JSONObject dataObject = response.getJSONObject(DATA);
                Course courses = gson.fromJson(dataObject.toString(),
                        courseType);
                completionHandler.onSuccess(courses);
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
