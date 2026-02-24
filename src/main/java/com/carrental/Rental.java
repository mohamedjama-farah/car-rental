package com.carrental;

public class Rental {

    private String customerName;
    private Car car;

    public Rental(String customerName, Car car) {
        this.customerName = customerName;
        this.car = car;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Car getCar() {
        return car;
    }

}