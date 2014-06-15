package com.alirezatr.uwcalendar.models;

public class Subject {
    private String subject;
    private String description;
    private String unit;

    public Subject(String subject, String description) {
        this.subject = subject;
        this.description = description;
    }

    public String getSubject() { return this.subject; }

    public String getDescription() { return this.description; }
}
