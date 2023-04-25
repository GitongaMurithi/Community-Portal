package com.example.communityportal;

public class JobsModel {
    String jobTitle,companyName,companyLocation;

    public JobsModel(String jobTitle, String companyName, String companyLocation) {
        this.jobTitle = jobTitle;
        this.companyName = companyName;
        this.companyLocation = companyLocation;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }
}
