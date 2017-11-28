package com.example.vnstarzynski.movingtonewwest;

import java.io.Serializable;

/**
 * Created by Phili on 11/27/2017.
 */

public class SportsField implements Serializable {
    private String parkName;
    private String activities;
    private double longitude;
    private double latitude;

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getActivities() {
        return activities;
    }

    public void setActivities(String activities) {
        this.activities = activities;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
