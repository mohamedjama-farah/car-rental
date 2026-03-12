package car.rental;

import java.util.List;


public interface CarRepository {

    // save a car to the storage
    void save(Car car);

    // find a car by its id — returns null if not found
    Car findById(int id);

    // return all cars
    List<Car> findAll();
}