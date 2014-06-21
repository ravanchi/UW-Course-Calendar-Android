package com.alirezatr.uwcalendar.utils;

import com.alirezatr.uwcalendar.network.RequestType;

import static com.alirezatr.uwcalendar.network.RequestKeys.API_KEY;
import static com.alirezatr.uwcalendar.network.RequestKeys.CLASS_REQUEST_URL;
import static com.alirezatr.uwcalendar.network.RequestKeys.COURSES_REQUEST_URL;
import static com.alirezatr.uwcalendar.network.RequestKeys.REQUEST_FORMAT;
import static com.alirezatr.uwcalendar.network.RequestKeys.SUBJECTS_REQUEST_URL;

public class StringUtils {

    public static String generateUrl(RequestType requestType, String subject) {
        return generateUrl(requestType, subject, null);
    }

    public static String generateUrl(RequestType requestType) {
        return generateUrl(requestType, null, null);
    }

    public static String generateUrl(RequestType requestType, String subject, String catalog_number) {
        if(requestType.equals(RequestType.COURSE)) {
            return COURSES_REQUEST_URL + subject + "/" + catalog_number + "." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestType.equals(RequestType.COURSES_LIST)) {
            return COURSES_REQUEST_URL + subject + "." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestType.equals(RequestType.CLASS)) {
            //TODO: Add the ability to change terms
            return CLASS_REQUEST_URL + "1139" + "/" + subject + "/" + catalog_number + "/schedule." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestType.equals(RequestType.SUBJECTS_LIST)) {
            return SUBJECTS_REQUEST_URL + "?key=" + API_KEY;
        }
        return "";
    }
}
