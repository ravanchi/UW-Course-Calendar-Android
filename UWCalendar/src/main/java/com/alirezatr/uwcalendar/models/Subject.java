package com.alirezatr.uwcalendar.models;

public class Subject {
    private String subject;
    private String description;
    private String unit;

    public Subject(String subject, String description, String unit) {
        this.subject = subject;
        this.description = description;
        this.unit = unit;
    }

    public String getSubject() { return this.subject; }

    public String getDescription() { return this.description; }

    public String getUnit() { return this.unit; }
}
