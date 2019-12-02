package com.example.tourmate.Model;

public class User {

    private  String UserId;
    private String Name;
    private String Email;
    private String profilePhoto;

    public User(String name, String email) {
        Name = name;
        Email = email;
    }

    public User(String name, String email, String profilePhoto) {
        Name = name;
        Email = email;
        this.profilePhoto = profilePhoto;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }
}
