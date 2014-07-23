package com.alirezatr.uwcalendar.models;

public class Class {
    private String subject;
    private String catalog_number;
    private int class_number;
    private String section;
    private int enrollment_capacity;
    private int enrollment_total;
    private int waiting_capacity;
    private String waiting_total;
    private String start_time;
    private String end_time;
    private String weekdays;
    private String location;
    private String room;
    private String instructor;

    public Class(String section, int class_number, int enrollment_capacity, int enrollment_total, String start_time, String end_time,
                 String weekdays, String location, String room, String instructor, int waiting_capacity, String waiting_total) {
        this.section = section;
        this.class_number = class_number;
        this.enrollment_capacity = enrollment_capacity;
        this.enrollment_total = enrollment_total;
        this.waiting_capacity = waiting_capacity;
        this.waiting_total = waiting_total;
        this.start_time = start_time;
        this.end_time = end_time;
        this.weekdays = weekdays;
        this.location = location;
        this.room = room;
        this.instructor = instructor;
    }

    public String getSection() { return this.section; }

    public int getClassNumber() { return this.class_number; }

    public int getEnrollmentCapacity() { return this.enrollment_capacity; }

    public int getEnrollmentTotal() { return this.enrollment_total; }

    public String getStartTime() { return start_time; }

    public String getEndTime() { return end_time; }

    public String getWeekdays() { return weekdays; }

    public String getLocation() { return this.location; }

    public void setLocation(String location) { this.location = location; }

    public String getRoom() { return this.room; }

    public void setRoom(String room) { this.room = room; }

    public String getInstructor() { return this.instructor; }

    public int getWaitingCapacity() { return this.waiting_capacity; }

    public String getWaitingTotal() { return this.waiting_total; }
}
