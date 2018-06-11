package com.example.lijunjie.hbrdnetworkofvehicles.bean;

import com.amap.api.maps.model.LatLng;

/**
 * Created by lijunjie on 2018/6/5.
 */

public class HistoricalTrack {

    private LatLng latLng;
    private double longitude;
    private double latitude;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
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
