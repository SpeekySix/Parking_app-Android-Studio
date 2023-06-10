package com.example.parking_app;

public class Parking {
    private String name;
    private String price;
    private String noCar;

    public Parking(String name, String price, String noCar) {
        this.name = name;
        this.price = price;
        this.noCar = noCar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNoCar() {
        return noCar;
    }

    public void setNoCar(String noCar) {
        this.noCar = noCar;
    }

    // Constructor, getters, and setters
}

