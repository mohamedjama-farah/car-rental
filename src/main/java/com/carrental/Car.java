package com.carrental;

public class Car {

    private String plate;
    private String make;
    private String model;

    public Car(String plate, String make, String model) {
        this.plate = plate;
        this.make = make;
        this.model = model;
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

}