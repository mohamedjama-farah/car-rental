package car.rental;

import com.google.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.NoSuchElementException;


public class CarRentalManager {

    private static final Logger logger =
            LogManager.getLogger(CarRentalManager.class);

    private final CarRepository repository;

    @Inject
    public CarRentalManager(CarRepository repository) {
        this.repository = repository;
    }

    public Car addNewCar(String make, String model) {
        logger.info("Adding new car: {} {}", make, model);
        Car car = new Car(make, model);
        repository.save(car);
        return car;
    }

    public void rentCar(int id) {
        logger.info("Renting car id={}", id);
        Car car = repository.findById(id);
        if (car == null) {
            throw new NoSuchElementException("Car not found: " + id);
        }
        if (!car.isAvailable()) {
            throw new IllegalStateException("Car is not available: " + id);
        }
        car.setAvailable(false);
        repository.save(car);
    }

    public void returnCar(int id) {
        logger.info("Returning car id={}", id);
        Car car = repository.findById(id);
        if (car == null) {
            throw new NoSuchElementException("Car not found: " + id);
        }
        if (car.isAvailable()) {
            throw new IllegalStateException("Car is already available: " + id);
        }
        car.setAvailable(true);
        repository.save(car);
    }

    public List<Car> getAllCars() {
        return repository.findAll();
    }
}