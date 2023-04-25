package com.example.communityportal;

public class User {
    public String email, password,logo;

    public User() {

    }

    public User(String email, String password, String logo) {
        this.email = email;
        this.password = password;
        this.logo=logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
