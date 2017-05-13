package com.example.vanhay.googlemap_test;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.Marker;

/**
 * Created by Van Hay on 11-May-17.
 */

public class UserPosition implements Parcelable {
    String displayname;
    String status;
    int marked;
    double latitude;
    double longitude;
    String timeSend;

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker) {
        this.marker = marker;
    }

    Marker marker;
    public UserPosition(){

    }
    public UserPosition( String displayname, String timeSend) {
        this.displayname = displayname;
        this.timeSend = timeSend;
    }

    public UserPosition(String displayname, String status, int marked, double latitude, double longitude, String timeSend) {
        this.displayname = displayname;
        this.status = status;
        this.marked = marked;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeSend = timeSend;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getMarked() {
        return marked;
    }

    public void setMarked(int marked) {
        this.marked = marked;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTimeSend() {
        return timeSend;
    }

    public void setTimeSend(String timeSend) {
        this.timeSend = timeSend;
    }

    /**
     *
     * These method below serve implementing Parcelable
     */
    protected UserPosition(Parcel in) {
        displayname = in.readString();
        status = in.readString();
        marked = in.readInt();
        latitude = in.readDouble();
        longitude = in.readDouble();
        timeSend = in.readString();
    }

    public static final Creator<UserPosition> CREATOR = new Creator<UserPosition>() {
        @Override
        public UserPosition createFromParcel(Parcel in) {
            return new UserPosition(in);
        }

        @Override
        public UserPosition[] newArray(int size) {
            return new UserPosition[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(displayname);
        dest.writeString(status);
        dest.writeInt(marked);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(timeSend);
    }
}