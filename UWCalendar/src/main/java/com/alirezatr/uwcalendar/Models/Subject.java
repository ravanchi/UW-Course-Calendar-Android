package com.alirezatr.uwcalendar.Models;

/**
 * Created by ali on 1/20/2014.
 */
public class Subject {
    private String subject;
    private String description;

    public Subject(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    public String getSubject() { return this.subject; }

    public String getDescription() { return this.description; }
}
