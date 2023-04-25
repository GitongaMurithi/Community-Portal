package com.example.communityportal;
//Firebase Model class
public class AddJob {
     String employer,description,qualification,title,location,logo;

    public AddJob() {
    }

    public AddJob(String employer, String description, String qualification,String title,String location,String logo) {
        this.employer = employer;
        this.description = description;
        this.qualification = qualification;
        this.title=title;
        this.location=location;
        this.logo=logo;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return employer;
    }

    public void setCompany(String company) {
        this.employer = company;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
