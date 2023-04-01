package com.example.thuchanh1;

public class Person {
    private String name;
    private String timeCheckIn;
    private String DateCheckIn;
    private String gender;
    private int img;


    public Person(String name, String timeCheckIn, String dateCheckIn, String gender, int img) {
        this.name = name;
        this.timeCheckIn = timeCheckIn;
        DateCheckIn = dateCheckIn;
        this.gender = gender;
        this.img = img;
    }

    public String getGender() {
        return gender;
    }

    public int getImg() {
        return img;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTimeCheckIn(String timeCheckIn) {
        this.timeCheckIn = timeCheckIn;
    }

    public void setDateCheckIn(String dateCheckIn) {
        DateCheckIn = dateCheckIn;
    }

    public String getName() {
        return name;
    }

    public String getTimeCheckIn() {
        return timeCheckIn;
    }

    public String getDateCheckIn() {
        return DateCheckIn;
    }
}
