package com.alirezatr.uwcalendar.listeners;

import org.json.JSONObject;

public interface NetworkResponseListener {
    void onSuccess(JSONObject response);
    void onError(Exception e);
}
