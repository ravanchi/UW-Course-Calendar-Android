package com.alirezatr.uwcalendar.network;

import static com.alirezatr.uwcalendar.network.RequestKeys.DATA;

import com.alirezatr.uwcalendar.listeners.SubjectsListener;
import com.alirezatr.uwcalendar.models.Subject;
import com.alirezatr.uwcalendar.utils.StringUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SubjectsRequest {
    private SubjectsListener completionHandler;
    private Gson gson = new Gson();
    Type subjectListType = new TypeToken<ArrayList<Subject>>(){}.getType();

    public SubjectsRequest(SubjectsListener completionHandler, RequestQueue requestQueue) {
        String url = StringUtils.generateUrl(SubjectsRequest.class, null, null);
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
                    JSONArray dataArray = response.getJSONArray(DATA);
                    ArrayList<Subject> subjectList = gson.fromJson(dataArray.toString(), subjectListType);
                    completionHandler.onSuccess(subjectList);
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
