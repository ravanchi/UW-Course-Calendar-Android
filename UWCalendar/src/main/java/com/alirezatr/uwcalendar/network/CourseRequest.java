package com.alirezatr.uwcalendar.network;

import static com.alirezatr.uwcalendar.network.RequestKeys.*;

import com.alirezatr.uwcalendar.listeners.CourseListener;
import com.alirezatr.uwcalendar.utils.NetworkUtils;
import com.alirezatr.uwcalendar.utils.StringUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class CourseRequest {
    private CourseListener completionHandler;

    public CourseRequest(String subject, String catalog_number, CourseListener completionHandler, RequestQueue requestQueue) {
        String url = StringUtils.generateURL(CourseRequest.class, subject, catalog_number);
        this.completionHandler = completionHandler;
        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null, successListener(), errorListener());
        requestQueue.add(newRequest);
    }

    private Response.Listener<JSONObject> successListener() {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject dataObject = response.getJSONObject("data");
                    completionHandler.onSuccess(NetworkUtils.parseCourse(dataObject));
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