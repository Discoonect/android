package com.test.places.model;

import java.io.Serializable;

public class Store implements Serializable {

    private String name;
    private String addr;
    private double lat;
    private double lng;

    public Store(){

    }

    public Store(String name, String addr, double lat, double lng) {
        this.name = name;
        this.addr = addr;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }
}
