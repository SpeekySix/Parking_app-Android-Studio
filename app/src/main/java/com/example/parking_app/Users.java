package com.example.parking_app;

import android.widget.TextView;

public class Users {

    String nameU;
    String emailU;
    String plate;

    public Users(String nameU, String emailU, String plate) {
        this.nameU = nameU;
        this.emailU = emailU;
        this.plate = plate;
    }

    public String getNameU() {
        return nameU;
    }

    public String getEmailU() {
        return emailU;
    }

    public String getPlate() {
        return plate;
    }
}
