package com.alirezatr.uwcalendar.utils;

import com.alirezatr.uwcalendar.network.RequestTypes;

import static com.alirezatr.uwcalendar.network.RequestKeys.API_KEY;
import static com.alirezatr.uwcalendar.network.RequestKeys.CLASS_REQUEST_URL;
import static com.alirezatr.uwcalendar.network.RequestKeys.COURSES_REQUEST_URL;
import static com.alirezatr.uwcalendar.network.RequestKeys.REQUEST_FORMAT;
import static com.alirezatr.uwcalendar.network.RequestKeys.SUBJECTS_REQUEST_URL;

public class StringUtils {

    public static String generateUrl(RequestTypes requestTypes, String subject) {
        return generateUrl(requestTypes, subject, null);
    }

    public static String generateUrl(RequestTypes requestTypes) {
        return generateUrl(requestTypes, null, null);
    }

    public static String generateUrl(RequestTypes requestTypes, String subject, String catalog_number) {
        if(requestTypes.equals(RequestTypes.COURSE)) {
            return COURSES_REQUEST_URL + subject + "/" + catalog_number + "." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestTypes.equals(RequestTypes.COURSES_LIST)) {
            return COURSES_REQUEST_URL + subject + "." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestTypes.equals(RequestTypes.CLASS)) {
            //TODO: Add the ability to change terms
            return CLASS_REQUEST_URL + "1149" + "/" + subject + "/" + catalog_number + "/schedule." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestTypes.equals(RequestTypes.SUBJECTS_LIST)) {
            return SUBJECTS_REQUEST_URL + "?key=" + API_KEY;
        }
        return "";
    }
}
