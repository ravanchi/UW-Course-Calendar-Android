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

/**
 * Created by ali on 1/20/2014.
 */
public class SubjectsRequest {
    private SubjectsListener completionHandler;
    private String url = subjectsRequestUrl + "?key=" + apiKey;

    public SubjectsRequest(SubjectsListener completionHandler, RequestQueue requestQueue) {
        this.completionHandler = completionHandler;
        JsonObjectRequest newRequest = new JsonObjectRequest(Request.Method.GET, url, null, successListener(), errorListener());
        requestQueue.add(newRequest);
    }

    private static ArrayList<Subject> parseResponse(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();

        ArrayList<Subject> subjectList = new ArrayList<Subject>(dataArrayLength);
        if(dataArrayLength != 0) {
            Subject subjectModel;
            JSONObject subjectData;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    subjectData = dataArray.getJSONObject(i);
                    subjectModel = new Subject(subjectData.getString(subject), subjectData.getString(description));
                    subjectList.add(subjectModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return subjectList;
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
