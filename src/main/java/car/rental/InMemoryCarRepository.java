package car.rental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * LOCATION: src/main/java/car/rental/InMemoryCarRepository.java
 *
 * This is a REAL implementation of CarRepository.
 * It stores cars in a HashMap in memory.
 *
 * Used in:
 *   - Integration tests (CarRentalManagerIT.java)
 *   - Later: replaced by MongoDB repository
 *
 * In unit tests we use a MOCK instead of this class.
 */
public class InMemoryCarRepository implements CarRepository {

    // HashMap: key = car id, value = car object
    private final Map<Integer, Car> store = new HashMap<>();

    @Override
    public void save(Car car) {
        store.put(car.getId(), car);
    }

    @Override
    public Car findById(int id) {
        return store.get(id);
    }

    @Override
    public List<Car> findAll() {
        return new ArrayList<>(store.values());
    }
}