package com.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class CarTest {

    @Test
    public void testCarHasPlate() {
        Car car = new Car("AB123CD", "Fiat", "Punto");
        assertEquals("AB123CD", car.getPlate());
    }

}