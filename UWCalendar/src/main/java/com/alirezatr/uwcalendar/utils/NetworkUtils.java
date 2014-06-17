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

    public static ArrayList<Subject> parseSubjectList(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();
        ArrayList<Subject> subjectList = new ArrayList<Subject>(dataArrayLength);

        if(dataArrayLength != 0) {
            Subject subjectModel;
            JSONObject subjectData;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    subjectData = dataArray.getJSONObject(i);
                    subjectModel = new Subject(subjectData.getString(SUBJECT),
                                               subjectData.getString(DESCRIPTION));
                    subjectList.add(subjectModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return subjectList;
    }

    public static Course parseCourse(JSONObject courseData) throws JSONException {
        Course courseModel;
        JSONArray instructions;
        List<String> instructionList = new ArrayList<String>();

        try {
            instructions = courseData.getJSONArray(INSTRUCTIONS);
            for(int i = 0; i < instructions.length(); i++) {
                instructionList.add(instructions.getString(i));
            }
            courseModel = new Course(courseData.getString(COURSE_ID),
                                     courseData.getString(SUBJECT),
                                     courseData.getString(CATALOG_NUMBER),
                                     courseData.getString(TITLE),
                                     courseData.getString(DESCRIPTION),
                                     instructionList,
                                     courseData.getString(PREREQUISITES),
                                     courseData.getString(ANTIREQUISITES),
                                     courseData.getString(NOTES));
        } catch(JSONException exception) {
            throw exception;
        }
        return courseModel;
    }

    public static ArrayList<Course> parseCourseList(JSONArray dataArray) throws JSONException {
        int dataArrayLength = dataArray.length();
        ArrayList<Course> courseList = new ArrayList<Course>(dataArrayLength);

        if(dataArrayLength != 0) {
            Course courseModel;
            JSONObject courseData;

            for(int i = 0; i < dataArrayLength; i++) {
                try {
                    courseData = dataArray.getJSONObject(i);
                    courseModel = new Course(courseData.getString(COURSE_ID),
                                             courseData.getString(SUBJECT),
                                             courseData.getString(CATALOG_NUMBER),
                                             courseData.getString(TITLE),
                                             courseData.getString(DESCRIPTION));
                    courseList.add(courseModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return courseList;
    }

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
                                               classData.getJSONArray(INSTRUCTORS).optString(0));
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
