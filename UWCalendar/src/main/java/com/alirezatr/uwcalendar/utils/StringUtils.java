package com.alirezatr.uwcalendar.utils;

import com.alirezatr.uwcalendar.network.ClassRequest;
import com.alirezatr.uwcalendar.network.CourseRequest;
import com.alirezatr.uwcalendar.network.CoursesRequest;
import com.alirezatr.uwcalendar.network.SubjectsRequest;

import static com.alirezatr.uwcalendar.network.RequestKeys.apiKey;
import static com.alirezatr.uwcalendar.network.RequestKeys.classRequestUrl;
import static com.alirezatr.uwcalendar.network.RequestKeys.coursesRequestUrl;
import static com.alirezatr.uwcalendar.network.RequestKeys.requestFormat;
import static com.alirezatr.uwcalendar.network.RequestKeys.subjectsRequestUrl;

public class StringUtils {

    public static String generateURL(Class<?> requestClass, String subject, String catalog_number) {
        if(requestClass.equals(CourseRequest.class)) {
            return coursesRequestUrl + subject + "/" + catalog_number + "." + requestFormat + "?key=" + apiKey;
        }
        if(requestClass.equals(CoursesRequest.class)) {
            return coursesRequestUrl + subject + "." + requestFormat + "?key=" + apiKey;
        }
        if(requestClass.equals(ClassRequest.class)) {
            //TODO: Add the ability to change terms
            return classRequestUrl + "1139" + "/" + subject + "/" + catalog_number + "/schedule." + requestFormat + "?key=" + apiKey;
        }
        if(requestClass.equals(SubjectsRequest.class)) {
            return subjectsRequestUrl + "?key=" + apiKey;
        }
        return "";
    }

}
