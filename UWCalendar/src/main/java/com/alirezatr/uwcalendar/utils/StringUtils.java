package com.alirezatr.uwcalendar.utils;

import com.alirezatr.uwcalendar.network.RequestType;

import static com.alirezatr.uwcalendar.network.RequestKeys.API_KEY;
import static com.alirezatr.uwcalendar.network.RequestKeys.CLASS_REQUEST_URL;
import static com.alirezatr.uwcalendar.network.RequestKeys.COURSES_REQUEST_URL;
import static com.alirezatr.uwcalendar.network.RequestKeys.REQUEST_FORMAT;
import static com.alirezatr.uwcalendar.network.RequestKeys.REQUEST_TERM;

public class StringUtils {

    public static String generateRequestUrl(RequestType requestType, String subject) {
        return generateRequestUrl(requestType, subject, null);
    }

    public static String generateRequestUrl(RequestType requestType, String courseSubject, String catalog_number) {
        if(requestType.equals(RequestType.COURSE)) {
            return COURSES_REQUEST_URL + courseSubject + "/" + catalog_number + "." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestType.equals(RequestType.COURSES_LIST)) {
            return COURSES_REQUEST_URL + courseSubject + "." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestType.equals(RequestType.CLASS)) {
            //TODO: Add the ability to change terms
            return CLASS_REQUEST_URL + REQUEST_TERM + "/" + courseSubject + "/" + catalog_number + "/schedule." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        return "";
    }
}
