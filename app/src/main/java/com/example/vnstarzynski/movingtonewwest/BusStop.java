package com.example.vnstarzynski.movingtonewwest;

import java.io.Serializable;

/**
 * Created by Phili on 11/28/2017.
 */

public class BusStop implements Serializable {
    private int stopNum;
    private String onStreet;
    private String atStreet;
    private String direction;
    private String position;
    private String status;
    private double longitude;
    private double latitude;

    public int getStopNum() {
        return stopNum;
    }

    public void setStopNum(int stopNum) {
        this.stopNum = stopNum;
    }

    public String getOnStreet() {
        return onStreet;
    }

    public void setOnStreet(String onStreet) {
        this.onStreet = onStreet;
    }

    public String getAtStreet() {
        return atStreet;
    }

    public void setAtStreet(String atStreet) {
        this.atStreet = atStreet;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
