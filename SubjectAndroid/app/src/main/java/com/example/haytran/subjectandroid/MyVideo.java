package com.example.haytran.subjectandroid;

import java.util.concurrent.TimeUnit;

/**
 * Created by Van Hay on 23-May-17.
 */

public class MyVideo {
    String name;
    String duration;
    String currentPosition;
    int durationMilisecond;
    int currentPositionMilisecond;
    String path;

    public MyVideo(String name, int durationMilisecond, int currentPositionMilisecond, String path) {
        this.name = name;
        this.durationMilisecond = durationMilisecond;
        this.currentPositionMilisecond = currentPositionMilisecond;
        this.path = path;
        convertToShow();
    }
    private void convertToShow () {
        duration = millisecondsToString(durationMilisecond);
        currentPosition = millisecondsToString(currentPositionMilisecond);
    }
    // Convert milisecond to time
    private String millisecondsToString(int milliseconds)  {
        long minutes = TimeUnit.MILLISECONDS.toMinutes((long) milliseconds);
        long seconds =  TimeUnit.MILLISECONDS.toSeconds((long) milliseconds - (minutes * 60000)) ;
        return minutes+":"+ seconds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCurrentPositionMilisecond() {
        return currentPositionMilisecond;
    }

    public void setCurrentPositionMilisecond(int currentPositionMilisecond) {
        this.currentPositionMilisecond = currentPositionMilisecond;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDurationMilisecond() {
        return durationMilisecond;
    }

    public void setDurationMilisecond(int durationMilisecond) {
        this.durationMilisecond = durationMilisecond;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(String currentPosition) {
        this.currentPosition = currentPosition;
    }

    @Override
    public String toString() {
        return  "Name: " + this.name
                +"\nDuration:" + duration
                + "\nSeen: " + currentPosition ;
    }
}
