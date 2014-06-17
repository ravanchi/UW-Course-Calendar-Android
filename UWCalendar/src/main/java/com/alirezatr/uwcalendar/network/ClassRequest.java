package com.alirezatr.uwcalendar.network;

import static com.alirezatr.uwcalendar.network.RequestKeys.DATA;

import com.alirezatr.uwcalendar.listeners.ClassesListener;
import com.alirezatr.uwcalendar.utils.NetworkUtils;
import com.alirezatr.uwcalendar.utils.StringUtils;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ClassRequest {
    private ClassesListener completionHandler;

    public ClassRequest(String subject, String catalog_number, ClassesListener completionHandler, RequestQueue requestQueue) {
        String url = StringUtils.generateURL(ClassRequest.class, subject, catalog_number);
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
                    completionHandler.onSuccess(NetworkUtils.parseClassList(dataArray));
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
