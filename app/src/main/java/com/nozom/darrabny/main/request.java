package com.nozom.darrabny.main;

import com.backendless.BackendlessUser;


public class request {
    private String grade;
    private String experience;
    private String objectId;
    private String academicYear;
    private String name;
    private  BackendlessUser owner;

    public BackendlessUser getOwner() {
        return owner;
    }

    public void setOwner(BackendlessUser owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public request(String name,String academicYear, String grade, String experience,BackendlessUser ownerId) {
        this.academicYear=academicYear;
        this.grade = grade;
        this.experience = experience;
        this.name=name;
        this.owner=ownerId;
    }
    public String getObjectId() {
        return objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getGrade() {
        return grade;
    }
    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getExperience() {
        return experience;
    }
    public void setExperience(String experience) {
        this.experience = experience;
    }
    public request(){
    }
    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }
    public String getAcademicYear() {
        return academicYear;
    }
}