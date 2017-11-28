package com.example.vnstarzynski.movingtonewwest;

import java.io.Serializable;

/**
 * Created by Phili on 11/28/2017.
 */

public class Shop implements Serializable {
    private String buildingName;
    private String address;
    private double longitude;
    private double latitude;

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
