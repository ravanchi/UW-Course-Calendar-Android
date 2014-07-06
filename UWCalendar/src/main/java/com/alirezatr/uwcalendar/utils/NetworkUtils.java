package com.alirezatr.uwcalendar.utils;

import com.alirezatr.uwcalendar.models.Class;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.alirezatr.uwcalendar.network.RequestKeys.*;

public class NetworkUtils {

    public static ArrayList<Class> parseClassList(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();
        ArrayList<Class> classList = new ArrayList<Class>(dataArrayLength);

        if(dataArrayLength != 0) {
            Class classModel;
            JSONObject courseData;
            JSONObject classData;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    courseData = dataArray.getJSONObject(i);
                    if(courseData != null && courseData.getJSONArray(CLASSES) != null) {
                        classData = courseData.getJSONArray(CLASSES).getJSONObject(0);
                        classModel = new Class(courseData.getString(SECTION),
                                               courseData.getInt(ENROLLMENT_CAPACITY),
                                               courseData.getInt(ENROLLMENT_TOTAL),
                                               classData.getJSONObject(DATE).getString(START_TIME),
                                               classData.getJSONObject(DATE).getString(END_TIME),
                                               classData.getJSONObject(DATE).getString(WEEKDAYS),
                                               classData.getJSONObject(LOCATION).getString(BUILDING),
                                               classData.getJSONObject(LOCATION).getString(ROOM),
                                               classData.getJSONArray(INSTRUCTORS).optString(0),
                                               courseData.optInt(WAITING_TOTAL),
                                               courseData.getString(WAITING_CAPACITY));
                        classList.add(classModel);
                    }
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return classList;
    }
}
