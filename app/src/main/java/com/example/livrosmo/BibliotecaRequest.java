package com.example.livrosmo;

public class BibliotecaRequest {
    private String name;
    private String address;
    private String openDays;
    private String openTime;
    private String closeTime;

    public BibliotecaRequest(String name, String address, String openDays, String openTime, String closeTime) {
        this.name = name;
        this.address = address;
        this.openDays = openDays;
        this.openTime = openTime;
        this.closeTime = closeTime;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getOpenDays() {
        return openDays;
    }

    public String getOpenTime() {
        return openTime;
    }

    public String getCloseTime() {
        return closeTime;
    }
}
