package com.carrental;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public class CarRepositoryTest {

    @Container
    static MongoDBContainer mongo = new MongoDBContainer("mongo:6.0");

    private CarMongoRepository repository;

    @BeforeEach
    public void setUp() {
        repository = new CarMongoRepository(
            mongo.getHost(),
            mongo.getMappedPort(27017)
        );
    }

    @AfterEach
    public void tearDown() {
        repository.dropCollection();
    }

    @Test
    public void testSaveAndFindAll() {
        Car car = new Car("AB123CD", "Fiat", "Punto");
        repository.save(car);
        List<Car> cars = repository.findAll();
        assertEquals(1, cars.size());
        assertEquals("AB123CD", cars.get(0).getPlate());
    }

    @Test
    public void testDelete() {
        Car car = new Car("AB123CD", "Fiat", "Punto");
        repository.save(car);
        repository.delete("AB123CD");
        assertTrue(repository.findAll().isEmpty());
    }

}