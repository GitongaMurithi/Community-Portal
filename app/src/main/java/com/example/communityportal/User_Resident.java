package com.example.communityportal;

public class User_Resident {
    public String
            username,
            phoneNo,
            password,
            email,
            profilePicture;

    public User_Resident() {

    }

    public User_Resident(String username, String phoneNo, String password, String email,String profilePicture) {


        this.username = username;
        this.phoneNo = phoneNo;
        this.password = password;
        this.email = email;
        this.profilePicture=profilePicture;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String image) {
        this.profilePicture = image;
    }
}
