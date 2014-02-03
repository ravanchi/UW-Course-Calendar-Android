package com.alirezatr.uwcalendar;

/**
 * Created by ali on 1/20/2014.
 */
public class RequestKeys {
    private RequestKeys() {
        throw new AssertionError();
    }

    public static final String apiKey = "624b3a3eab10f59832f55b39f6900947";

    public static final String subjectsRequestUrl = "https://api.uwaterloo.ca/v2/codes/subjects.json";
    public static final String coursesRequestUrl = "https://api.uwaterloo.ca/v2/courses/";

    public static final String subject = "subject";
    public static final String description = "description";

}
