package com.alirezatr.uwcalendar.models;

import java.util.HashMap;
import java.util.List;

public class Course {
    private String subject;
    private String catalog_number;
    private String title;
    private double units;
    private String description;
    private List<String> instructions;
    private String prerequisites;
    private String antirequisites;
    private List<String> terms_offered;
    private String notes;
    private String url;
    private HashMap<String, Boolean> offerings;

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

    public HashMap<String, Boolean> getOfferings() { return this.offerings; }
}
