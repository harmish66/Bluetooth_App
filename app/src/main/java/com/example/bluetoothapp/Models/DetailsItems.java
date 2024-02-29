package com.example.bluetoothapp.Models;

public class DetailsItems {

    private String name;
    private String address;
    private String type;
    private String bettery;
    public DetailsItems(String name, String address, String type, String bettery) {
        this.name = name;
        this.address = address;
        this.type = type;
        this.bettery = bettery;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBettery() {
        return bettery;
    }

    public void setBettery(String bettery) {
        this.bettery = bettery;
    }


}
