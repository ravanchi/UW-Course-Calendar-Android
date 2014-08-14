package com.alirezatr.uwcalendar.models;

public final class ListItem extends ListRow {
    public Class clazz;
    public Course course;
    public Subject subject;

    public ListItem(Class clazz) {
        this.clazz = clazz;
    }

    public ListItem(Course course) {
        this.course = course;
    }

    public ListItem(Subject subject) {
        this.subject = subject;
    }
}