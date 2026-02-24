package com.carrental;

public class Car {

    private String plate;
    private String make;
    private String model;
    private boolean available;

    public Car(String plate, String make, String model) {
        this.plate = plate;
        this.make = make;
        this.model = model;
        this.available = true;
    }

    public String getPlate() {
        return plate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

}
