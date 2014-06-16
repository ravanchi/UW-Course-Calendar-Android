package com.alirezatr.uwcalendar.utils;

import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.models.Class;
import com.alirezatr.uwcalendar.models.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.alirezatr.uwcalendar.network.RequestKeys.*;

public class NetworkUtils {

    public static ArrayList<Subject> parseSubjects(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();

        ArrayList<Subject> subjectList = new ArrayList<Subject>(dataArrayLength);
        if(dataArrayLength != 0) {
            Subject subjectModel;
            JSONObject subjectData;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    subjectData = dataArray.getJSONObject(i);
                    subjectModel = new Subject(subjectData.getString(SUBJECT), subjectData.getString(DESCRIPTION));
                    subjectList.add(subjectModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return subjectList;
    }

    public static Course parseCourse(JSONObject dataObject) throws JSONException {
        Course courseModel;
        JSONObject courseData;
        JSONArray instructions;

        try {
            courseData = dataObject;
            List<String> instructionList = new ArrayList<String>();
            instructions = courseData.getJSONArray(INSTRUCTIONS);
            for(int i = 0; i < instructions.length(); i++) {
                instructionList.add(instructions.getString(i));
            }
            courseModel = new Course(courseData.getString(COURSE_ID), courseData.getString(SUBJECT), courseData.getString(CATALOG_NUMBER),
                    courseData.getString(TITLE), courseData.getString(DESCRIPTION), instructionList, courseData.getString(PREREQUISITES),
                    courseData.getString(ANTIREQUISITES), courseData.getString(NOTES));
        } catch(JSONException exception) {
            throw exception;
        }
        return courseModel;
    }

    public static ArrayList<Course> parseCourses(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();

        ArrayList<Course> courseList = new ArrayList<Course>(dataArrayLength);
        if(dataArrayLength != 0) {
            Course courseModel;
            JSONObject courseData;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    courseData = dataArray.getJSONObject(i);
                    courseModel = new Course(courseData.getString(COURSE_ID), courseData.getString(SUBJECT), courseData.getString(CATALOG_NUMBER),
                            courseData.getString(TITLE), courseData.getString(DESCRIPTION));
                    courseList.add(courseModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return courseList;
    }

    public static ArrayList<Class> parseClass(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();

        ArrayList<Class> classList = new ArrayList<Class>(dataArrayLength);
        if(dataArrayLength != 0) {
            Class classModel;
            JSONArray classData;
            JSONObject courseData;

            String start_time;
            String end_time;
            String weekdays;

            String building;
            String rooms;
            String instructor;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    courseData = dataArray.getJSONObject(i);
                    classData = courseData.getJSONArray(CLASSES);

                    building = classData.getJSONObject(0).getJSONObject(LOCATION).getString(BUILDING);
                    rooms = classData.getJSONObject(0).getJSONObject(LOCATION).getString(ROOM);
                    instructor = classData.getJSONObject(0).getJSONArray(INSTRUCTORS).optString(0);
                    start_time = classData.getJSONObject(0).getJSONObject(DATE).getString(START_TIME);
                    end_time = classData.getJSONObject(0).getJSONObject(DATE).getString(END_TIME);
                    weekdays = classData.getJSONObject(0).getJSONObject(DATE).getString(WEEKDAYS);

                    classModel = new Class(courseData.getString(SECTION), courseData.getInt(ENROLLMENT_CAPACITY), courseData.getInt(ENROLLMENT_TOTAL),
                            start_time, end_time, weekdays, building, rooms, instructor);
                    classList.add(classModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return classList;
    }
}
