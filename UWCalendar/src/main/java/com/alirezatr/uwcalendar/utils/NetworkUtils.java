package com.alirezatr.uwcalendar.utils;

import com.alirezatr.uwcalendar.models.Course;
import com.alirezatr.uwcalendar.models.Class;
import com.alirezatr.uwcalendar.models.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.alirezatr.uwcalendar.network.RequestKeys.description;
import static com.alirezatr.uwcalendar.network.RequestKeys.subject;

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
                    subjectModel = new Subject(subjectData.getString(subject), subjectData.getString(description));
                    subjectList.add(subjectModel);
                } catch(JSONException exception) {
                    throw exception;
                }
            }
        }
        return subjectList;
    }

    public static Course parseCourse(JSONObject dataObject) throws JSONException {
        Course courseModel = null;
        JSONObject courseData;
        JSONArray instructions;

        try {
            courseData = dataObject;
            List<String> instructionList = new ArrayList<String>();
            instructions = courseData.getJSONArray("instructions");
            for(int i = 0; i < instructions.length(); i++) {
                instructionList.add(instructions.getString(i));
            }
            courseModel = new Course(courseData.getString("course_id"), courseData.getString("subject"), courseData.getString("catalog_number"),
                    courseData.getString("title"), courseData.getString("description"), instructionList, courseData.getString("prerequisites"), courseData.getString("antirequisites"), courseData.getString("notes"));
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
                    courseModel = new Course(courseData.getString("course_id"), courseData.getString("subject"), courseData.getString("catalog_number"),
                            courseData.getString("title"), courseData.getString("description"));
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
                    classData = courseData.getJSONArray("classes");

                    building = classData.getJSONObject(0).getJSONObject("location").getString("building");
                    rooms = classData.getJSONObject(0).getJSONObject("location").getString("room");
                    instructor = classData.getJSONObject(0).getJSONArray("instructors").optString(0);
                    start_time = classData.getJSONObject(0).getJSONObject("date").getString("start_time");
                    end_time = classData.getJSONObject(0).getJSONObject("date").getString("end_time");
                    weekdays = classData.getJSONObject(0).getJSONObject("date").getString("weekdays");

                    classModel = new Class(courseData.getString("section"), courseData.getInt("enrollment_capacity"), courseData.getInt("enrollment_total"),
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
