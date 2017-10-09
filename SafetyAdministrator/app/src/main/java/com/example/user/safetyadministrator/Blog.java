package com.example.user.safetyadministrator;

/**
 * Created by user on 5/4/2017.
 */

public class Blog {
    private String phone_Number;
    private String description;
    private String age;
    private String gender;
    private String image;
    private String latitude;
    private String longitude;
    private String username;

    public Blog(){

    }

    public Blog(String phone_Number, String description, String age, String gender, String image, String latitude, String longitude, String username) {
        this.phone_Number = phone_Number;
        this.description = description;
        this.age = age;
        this.gender = gender;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
        this.username = username;
    }

    public String getPhone_Number() {
        return phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        this.phone_Number = phone_Number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
