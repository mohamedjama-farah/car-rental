package car.rental;

import java.util.List;

/**
 * LOCATION: src/main/java/car/rental/CarRepository.java
 *
 * This is an INTERFACE — it defines what operations are possible
 * on a car storage, but does NOT say how they are implemented.
 *
 * In tests  → we use a MOCK of this interface (Mockito)
 * In real app → we use a real implementation (MongoDB, MySQL etc.)
 *
 * CarRentalManager only knows about this interface.
 * It does not care if cars are stored in memory, a file, or a database.
 */
public interface CarRepository {

    // save a car to the storage
    void save(Car car);

    // find a car by its id — returns null if not found
    Car findById(int id);

    // return all cars
    List<Car> findAll();
}