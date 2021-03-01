package com.example.report_it.NewsSegment;

public class Model {
    String image;
    String title;
    String date;
    String author;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    String source;

    public Model(String image, String title, String date, String author,String source) {
        this.image = image;
        this.title = title;
        this.date = date;
        this.author = author;
        this.source=source;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
