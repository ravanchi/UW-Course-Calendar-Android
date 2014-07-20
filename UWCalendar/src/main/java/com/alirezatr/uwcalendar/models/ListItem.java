package com.alirezatr.uwcalendar.models;

public final class ListItem extends ListRow {
    public Class clazz = null;
    public Course course = null;
    public Subject subject = null;

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