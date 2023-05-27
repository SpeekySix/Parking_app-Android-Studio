package com.example.admin_app;

public class LotsItem {
    String lotID;
    String lotName;
    String lotPrice;

    public LotsItem() {
    }

    public LotsItem(String lotID, String lotName, String lotPrice) {
        this.lotID = lotID;
        this.lotName = lotName;
        this.lotPrice = lotPrice;
    }

    public String getLotID() {
        return lotID;
    }

    public void setLotID(String lotID) {
        this.lotID = lotID;
    }

    public String getLotName() {
        return lotName;
    }

    public void setLotName(String lotName) {
        this.lotName = lotName;
    }

    public String getLotPrice() {
        return lotPrice;
    }

    public void setLotPrice(String lotPrice) {
        this.lotPrice = lotPrice;
    }

}
