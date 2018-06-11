package com.example.lijunjie.hbrdnetworkofvehicles.bean;


import com.amap.api.maps.model.LatLng;

/**
 * Created by lijunjie on 2018/5/5.
 */

public class Car {


    private LatLng latLng;
    private String id;
    private String info;

    public Car(String id, String latLng, String info) {
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Car(LatLng latLng, String id, String info) {
        this.latLng = latLng;
        this.id = id;
        this.info = info;
    }
}
