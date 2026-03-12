package car.rental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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