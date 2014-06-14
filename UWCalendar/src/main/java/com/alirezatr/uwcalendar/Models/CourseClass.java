package com.alirezatr.uwcalendar.Models;

/**
 * Created by ali on 1/20/2014.
 */
public class CourseClass {
    private String section;
    private String enrollment_capacity;
    private String enrollment_total;

    private String start_time;
    private String end_time;
    private String weekdays;
    private String location;
    private String room;
    private String instructor;

    public CourseClass(String section, String enrollment_capacity, String enrollment_total, String start_time, String end_time,
                       String weekdays, String location, String room, String instructor) {
        this.section = section;
        this.enrollment_capacity = enrollment_capacity;
        this.enrollment_total = enrollment_total;
        this.start_time = start_time;
        this.end_time = end_time;
        this.weekdays = weekdays;
        this.location = location;
        this.room = room;
        this.instructor = instructor;
    }

    public String getSection() { return this.section; }

    public String getEnrollment_capacity() { return this.enrollment_capacity; }

    public String getEnrollment_total() { return this.enrollment_total; }

    public String getStart_time() { return start_time; }

    public String getEnd_time() { return end_time; }

    public String getWeekdays() { return weekdays; }

    public String getLocation() { return this.location; }

    public void setLocation(String location) { this.location = location; }

    public String getRoom() { return this.room; }

    public void setRoom(String room) { this.room = room; }

    public String getInstructor() { return this.instructor; }
}
