package car.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * LOCATION: src/test/java/car/rental/CarRentalManagerIT.java
 *
 * IT = Integration Test
 * Uses a REAL InMemoryCarRepository — no mocks.
 * Run with: mvn verify
 *
 * Difference from CarRentalManagerTest:
 *   CarRentalManagerTest  → uses @Mock repository (unit test)
 *   CarRentalManagerIT    → uses real InMemoryCarRepository (integration test)
 */
class CarRentalManagerIT {

    private CarRentalManager manager;

    @BeforeEach
    void setUp() {
        Car.resetIdCounter();
        Rental.resetIdCounter();
        // use a REAL repository — no mocks
        manager = new CarRentalManager(new InMemoryCarRepository());
    }

    @Test
    void testAddNewCarAndRetrieveIt() {
        manager.addNewCar("Toyota", "Corolla");
        List<Car> cars = manager.getAllCars();
        assertThat(cars).hasSize(1);
    }

    @Test
    void testAddMultipleCarsAndRetrieveThem() {
        manager.addNewCar("Toyota", "Corolla");
        manager.addNewCar("Honda", "Civic");
        List<Car> cars = manager.getAllCars();
        assertThat(cars).hasSize(2);
    }

    @Test
    void testRentCarMakesItUnavailable() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        manager.rentCar(car.getId());
        List<Car> cars = manager.getAllCars();
        assertThat(cars.get(0).isAvailable()).isFalse();
    }

    @Test
    void testReturnCarMakesItAvailableAgain() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        manager.rentCar(car.getId());
        manager.returnCar(car.getId());
        List<Car> cars = manager.getAllCars();
        assertThat(cars.get(0).isAvailable()).isTrue();
    }

    @Test
    void testRentCarThatDoesNotExistThrows() {
        assertThatThrownBy(() -> manager.rentCar(999))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testReturnCarThatDoesNotExistThrows() {
        assertThatThrownBy(() -> manager.returnCar(999))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void testRentAlreadyRentedCarThrows() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        manager.rentCar(car.getId());
        assertThatThrownBy(() -> manager.rentCar(car.getId()))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void testReturnCarThatWasNotRentedThrows() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        assertThatThrownBy(() -> manager.returnCar(car.getId()))
                .isInstanceOf(IllegalStateException.class);
    }
}