package edu.harrisburgu.cisc349.cameratest;

import android.graphics.Bitmap;

public class ImageData {
    private Bitmap image;
    private String comment;
    private String name;
    private String dateTime;

    public ImageData(Bitmap image, String comment, String name, String dateTime) {
        this.image = image;
        this.comment = comment;
        this.name = name;
        this.dateTime = dateTime;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getComment() {
        return comment;
    }

    public String getName() {return name;}

    public String getDateTime() {return dateTime;}
}
