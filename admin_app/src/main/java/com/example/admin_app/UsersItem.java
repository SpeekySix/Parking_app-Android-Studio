package com.example.admin_app;

public class UsersItem {

    String nameU, emailU, plate;

    UsersItem(){

    }

    public UsersItem(String nameU, String emailU, String plate) {
        this.nameU = nameU;
        this.emailU = emailU;
        this.plate = plate;
    }

    public String getNameU() {
        return nameU;
    }

    public void setNameU(String nameU) {
        this.nameU = nameU;
    }

    public String getEmailU() {
        return emailU;
    }

    public void setEmailU(String emailU) {
        this.emailU = emailU;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }
}
