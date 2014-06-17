package com.alirezatr.uwcalendar.network;

public class RequestKeys {
    private RequestKeys() {
        throw new AssertionError();
    }

    public static final String API_KEY = "624b3a3eab10f59832f55b39f6900947";
    public static final String REQUEST_FORMAT = "json";

    public static final String SUBJECTS_REQUEST_URL = "https://api.uwaterloo.ca/v2/codes/subjects.json";
    public static final String COURSES_REQUEST_URL = "https://api.uwaterloo.ca/v2/courses/";
    public static final String CLASS_REQUEST_URL = "https://api.uwaterloo.ca/v2/terms/";

    public static final String DATA = "data";
    public static final String SUBJECT = "subject";
    public static final String DESCRIPTION = "description";
    public static final String INSTRUCTIONS = "instructions";
    public static final String COURSE_ID = "course_id";
    public static final String CATALOG_NUMBER = "catalog_number";
    public static final String TITLE = "title";
    public static final String PREREQUISITES = "prerequisites";
    public static final String ANTIREQUISITES = "antirequisites";
    public static final String NOTES = "notes";
    public static final String CLASSES = "classes";
    public static final String LOCATION = "location";
    public static final String BUILDING = "building";
    public static final String ROOM = "room";
    public static final String DATE = "date";
    public static final String START_TIME = "start_time";
    public static final String INSTRUCTORS = "instructors";
    public static final String END_TIME = "end_time";
    public static final String WEEKDAYS = "weekdays";
    public static final String SECTION = "section";
    public static final String ENROLLMENT_CAPACITY = "enrollment_capacity";
    public static final String ENROLLMENT_TOTAL = "enrollment_total";
}
