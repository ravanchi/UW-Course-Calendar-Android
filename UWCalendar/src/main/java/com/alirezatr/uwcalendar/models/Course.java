package com.alirezatr.uwcalendar.models;

import java.util.List;

public class Course {
    private String course_id;
    private String subject;
    private String catalog_number;
    private String title;
    private double units;
    private String description;
    private List<String> instructions;
    private String prerequisites;
    private String antirequisites;
    private String corequisites;
    private String crosslistings;
    private List<String> terms_offered;
    private String notes;
    private String calendar_year;
    private String url;
    private String academic_level;

    public Course(String course_id, String subject, String catalog_number, String title, String description) {
        this.course_id = course_id;
        this.subject = subject;
        this.catalog_number = catalog_number;
        this.title = title;
        this.description = description;
    }

    public Course(String course_id, String subject, String catalog_number, String title, double units, String description,
                  List<String> instructions, String prerequisites, String antirequisites, String corequisites,
                  String crosslistings, List<String> terms_offered, String notes, String calendar_year, String url,
                  String academic_level) {
        this(course_id, subject, catalog_number, title, description);
        this.instructions = instructions;
        this.prerequisites = prerequisites;
        this.antirequisites = antirequisites;
        this.notes = notes;
        this.units = units;
        this.terms_offered = terms_offered;
        this.url = url;
        this.academic_level = academic_level;
        this.crosslistings = crosslistings;
        this.corequisites = corequisites;
        this.calendar_year = calendar_year;
    }

    public String getCourseId() { return this.course_id; }

    public String getSubject() { return this.subject; }

    public String getCatalogNumber() { return this.catalog_number; }

    public String getTitle() { return this.title; }

    public double getUnits() { return this.units; }

    public String getDescription() { return this.description; }

    public String getPrerequisites() { return this.prerequisites; }

    public String getAntirequisites() { return this.antirequisites; }

    public String getNotes() { return this.notes; }

    public List<String> getInstructions() { return this.instructions; }

    public List<String> getTermsOffered() { return this.terms_offered; }

    public String getUrl() { return this.url; }

    public String getAcademicLevel() { return this.academic_level; }

    public String getCorequisites() { return this.corequisites; }

    public String getCrosslistings() { return this.crosslistings; }

    public String getCalendarYear() { return this.calendar_year; }
}
