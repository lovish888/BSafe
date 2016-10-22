package com.insane.lovish.informe;

import android.graphics.Bitmap;

/**
 * Created by LovishJain on 06-Oct-16.
 */

public class CrimeDetailsVariables {

    private Bitmap crimeImage;
    private String title, author, timePassed;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTimePassed() {
        return timePassed;
    }

    public void setTimePassed(String timePassed) {
        this.timePassed = timePassed;
    }

    public Bitmap getCrimeImage() {
        return crimeImage;
    }

    public void setCrimeImage(Bitmap crimeImage) {
        this.crimeImage = crimeImage;
    }
}
