package com.example.bluetoothapp.Models;

public class DeviceItems {
    private String name;
    private String address;




    public DeviceItems(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }



}
