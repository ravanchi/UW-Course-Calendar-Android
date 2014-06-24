package com.alirezatr.uwcalendar.listeners;

import org.json.JSONObject;

public interface BaseResponseListener {
    void onSuccess(JSONObject response);
    void onError(Exception e);
}
