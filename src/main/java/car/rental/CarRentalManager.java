package car.rental;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LOCATION: src/main/java/car/rental/CarRentalManager.java
 *
 * V8 changes from V7:
 *   - REMOVED: private List<Car> cars = new ArrayList<>()
 *   - ADDED:   private CarRepository repository
 *   - ADDED:   constructor takes CarRepository as parameter
 *
 * This is called CONSTRUCTOR INJECTION — the manager receives
 * its dependency (repository) from outside instead of creating it.
 * This makes it easy to swap real repository with a mock in tests.
 *
 * IMPORTANT RULE:
 *   NEVER mock the class under test (CarRentalManager)
 *   ONLY mock its collaborators (CarRepository)
 */
public class CarRentalManager {

    private static final Logger LOGGER =
            LogManager.getLogger(CarRentalManager.class);

    // ── NEW in V8: repository replaces ArrayList ───────────────────────
    private final CarRepository repository;

    // ── NEW in V8: constructor receives repository from outside ────────
    public CarRentalManager(CarRepository repository) {
        this.repository = repository;
    }

    // ── ADD A NEW CAR ──────────────────────────────────────────────────
    public Car addNewCar(String make, String model) {
        LOGGER.info("Adding new car: {} {}", make, model);
        Car car = new Car(make, model);
        // ── was: cars.add(car) ─────────────────────────────────────────
        repository.save(car);
        return car;
    }

    // ── RENT A CAR ─────────────────────────────────────────────────────
    public void rentCar(int carId) {
        LOGGER.info("Renting car id={}", carId);
        Car car = findCarById(carId);
        if (!car.isAvailable()) {
            throw new IllegalStateException(
                    "Car is not available: " + carId);
        }
        car.setAvailable(false);
        repository.save(car);
    }

    // ── RETURN A CAR ───────────────────────────────────────────────────
    public void returnCar(int carId) {
        LOGGER.info("Returning car id={}", carId);
        Car car = findCarById(carId);
        if (car.isAvailable()) {
            throw new IllegalStateException(
                    "Car is already available: " + carId);
        }
        car.setAvailable(true);
        repository.save(car);
    }

    // ── GET ALL CARS ───────────────────────────────────────────────────
    public List<Car> getAllCars() {
        // ── was: return cars ───────────────────────────────────────────
        return repository.findAll();
    }

    // ── PRIVATE HELPER ─────────────────────────────────────────────────
    private Car findCarById(int carId) {
        Car car = repository.findById(carId);
        if (car == null) {
            throw new NoSuchElementException("Car not found: " + carId);
        }
        return car;
    }
}