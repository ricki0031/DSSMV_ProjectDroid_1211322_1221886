package com.example.livrosmo;

public class Time {
    private int hour;
    private int minute;
    private int second;
    private int nano;

    public Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
        this.second = 0; // Sempre 0
        this.nano = 0;   // Sempre 0
    }

    // Getters e setters (opcional, se precisar)
    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }
}
