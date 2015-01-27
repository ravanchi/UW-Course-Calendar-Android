package com.alirezatr.uwcalendar.models;

public final class ListItem extends ListRow {
    public Subject subject;
    public Course course;
    public Class courseClass;

    public ListItem(Subject subject) {
        this.subject = subject;
    }

    public ListItem(Course course) {
        this.course = course;
    }

    public ListItem(Class courseClass) {
        this.courseClass = courseClass;
    }
}