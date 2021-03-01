package com.example.report_it.WantedCriminalClasses;

public class Wanted {
    String Alias,Crime,Eyes,Gender,Hair,Height,Last_Seen,Name,Place_DOB,Skin_Color,Image;

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Wanted() {
    }

    public String getAlias() {
        return Alias;
    }

    public void setAlias(String alias) {
        Alias = alias;
    }

    public String getCrime() {
        return Crime;
    }

    public void setCrime(String crime) {
        Crime = crime;
    }

    public String getEyes() {
        return Eyes;
    }

    public void setEyes(String eyes) {
        Eyes = eyes;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHair() {
        return Hair;
    }

    public void setHair(String hair) {
        Hair = hair;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getLast_Seen() {
        return Last_Seen;
    }

    public void setLast_Seen(String last_Seen) {
        Last_Seen = last_Seen;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPlace_DOB() {
        return Place_DOB;
    }

    public void setPlace_DOB(String place_DOB) {
        Place_DOB = place_DOB;
    }

    public String getSkin_Color() {
        return Skin_Color;
    }

    public void setSkin_Color(String skin_Color) {
        Skin_Color = skin_Color;
    }

    public Wanted(String image, String alias, String crime, String eyes, String gender, String hair, String height, String last_Seen, String name, String place_DOB, String skin_Color) {
        Alias = alias;
        Crime = crime;
        Eyes = eyes;
        Gender = gender;
        Hair = hair;
        Height = height;
        Last_Seen = last_Seen;
        Name = name;
        Place_DOB = place_DOB;
        Skin_Color = skin_Color;
        image=image;
    }

}
