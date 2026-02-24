package com.carrental;

import java.util.List;

public interface CarRepository {

    void save(Car car);

    List<Car> findAll();

    void delete(String plate);

}