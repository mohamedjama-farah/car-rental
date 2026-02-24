package com.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class RentalTest {

    @Test
    public void testRentalHasCustomerName() {
        Car car = new Car("AB123CD", "Fiat", "Punto");
        Rental rental = new Rental("Mario Rossi", car);
        assertEquals("Mario Rossi", rental.getCustomerName());
    }

    @Test
    public void testRentalHasCar() {
        Car car = new Car("AB123CD", "Fiat", "Punto");
        Rental rental = new Rental("Mario Rossi", car);
        assertEquals(car, rental.getCar());
    }

}