package com.example.report_it.MissingPeopleClasses;

public class Missing {
    public String getClothes_Details() {
        return Clothes_Details;
    }

    public void setClothes_Details(String clothes_Details) {
        Clothes_Details = clothes_Details;
    }

    public String getContact_Details() {
        return Contact_Details;
    }

    public void setContact_Details(String contact_Details) {
        Contact_Details = contact_Details;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getHeight() {
        return Height;
    }

    public void setHeight(String height) {
        Height = height;
    }

    public String getLast_Seen_Location() {
        return Last_Seen_Location;
    }

    public void setLast_Seen_Location(String last_Seen_Location) {
        Last_Seen_Location = last_Seen_Location;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSkin_Color() {
        return Skin_Color;
    }

    public void setSkin_Color(String skin_Color) {
        Skin_Color = skin_Color;
    }

    private String Clothes_Details,Gender,Height,Last_Seen_Location,Name,Skin_Color,Contact_Details,Image;
    public Missing(){ }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public Missing(String Image, String Clothes_Details, String Contact_Details, String Gender, String Height, String Last_Seen_Location, String Name, String Skin_Color)
    {
        this.Clothes_Details=Clothes_Details;
        this.Contact_Details=Contact_Details;
        this.Gender=Gender;
        this.Height=Height;
        this.Last_Seen_Location=Last_Seen_Location;
        this.Name=Name;
        this.Skin_Color=Skin_Color;
        this.Image=Image;
    }
}
