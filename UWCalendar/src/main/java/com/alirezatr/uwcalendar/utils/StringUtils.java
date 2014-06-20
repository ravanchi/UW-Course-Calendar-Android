package com.alirezatr.uwcalendar.utils;

import com.alirezatr.uwcalendar.network.ClassRequest;
import com.alirezatr.uwcalendar.network.CourseRequest;
import com.alirezatr.uwcalendar.network.CoursesRequest;
import com.alirezatr.uwcalendar.network.SubjectsRequest;

import static com.alirezatr.uwcalendar.network.RequestKeys.API_KEY;
import static com.alirezatr.uwcalendar.network.RequestKeys.CLASS_REQUEST_URL;
import static com.alirezatr.uwcalendar.network.RequestKeys.COURSES_REQUEST_URL;
import static com.alirezatr.uwcalendar.network.RequestKeys.REQUEST_FORMAT;
import static com.alirezatr.uwcalendar.network.RequestKeys.SUBJECTS_REQUEST_URL;

public class StringUtils {

    public static String generateURL(Class<?> requestClass, String subject, String catalog_number) {
        if(requestClass.equals(CourseRequest.class)) {
            return COURSES_REQUEST_URL + subject + "/" + catalog_number + "." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestClass.equals(CoursesRequest.class)) {
            return COURSES_REQUEST_URL + subject + "." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestClass.equals(ClassRequest.class)) {
            //TODO: Add the ability to change terms
            return CLASS_REQUEST_URL + "1139" + "/" + subject + "/" + catalog_number + "/schedule." + REQUEST_FORMAT + "?key=" + API_KEY;
        }
        if(requestClass.equals(SubjectsRequest.class)) {
            return SUBJECTS_REQUEST_URL + "?key=" + API_KEY;
        }
        return "";
    }
}
