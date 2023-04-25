package com.example.communityportal;

//Firebase model

public class User_Authority {
    public String
            username,
            phoneNo,
            password,
            email,
            area;

    public User_Authority() {

    }

    public User_Authority(String username, String phoneNo, String password, String email,String area) {


        this.username = username;
        this.phoneNo = phoneNo;
        this.password = password;
        this.email = email;
        this.area=area;

    }

}
