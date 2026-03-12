package car.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class CarRentalManagerTest {

    @Mock
    private CarRepository repository;

    @InjectMocks
    private CarRentalManager manager;

    @BeforeEach
    void setUp() {
        Car.resetIdCounter();
    }

    // ── TESTS FOR: addNewCar() ─────────────────────────────────────────

    @Test
    void testAddNewCarReturnsACar() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        assertThat(car).isNotNull();
    }

    @Test
    void testAddNewCarStoresMake() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        assertThat(car.getMake()).isEqualTo("Toyota");
    }

    @Test
    void testAddNewCarStoresModel() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        assertThat(car.getModel()).isEqualTo("Corolla");
    }

    @Test
    void testAddNewCarIsAvailable() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        assertThat(car.isAvailable()).isTrue();
    }

    @Test
    void testAddNewCarSavesToRepository() {
        Car car = manager.addNewCar("Toyota", "Corolla");
        // verify that repository.save() was called with the car
        verify(repository).save(car);
    }

    @Test
    void testGetAllCarsReturnsAllCars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        when(repository.findAll()).thenReturn(Arrays.asList(car1, car2));
        List<Car> cars = manager.getAllCars();
        assertThat(cars).hasSize(2);
    }

    // ── TESTS FOR: rentCar() ───────────────────────────────────────────

    @Test
    void testRentCarMakesCarUnavailable() {
        Car car = new Car("Toyota", "Corolla");
        when(repository.findById(car.getId())).thenReturn(car);
        manager.rentCar(car.getId());
        assertThat(car.isAvailable()).isFalse();
    }

    @Test
    void testRentCarSavesToRepository() {
        Car car = new Car("Toyota", "Corolla");
        when(repository.findById(car.getId())).thenReturn(car);
        manager.rentCar(car.getId());
        verify(repository).save(car);
    }

    @Test
    void testRentAlreadyRentedCarThrows() {
        Car car = new Car("Toyota", "Corolla");
        car.setAvailable(false);
        when(repository.findById(car.getId())).thenReturn(car);
        assertThatThrownBy(() -> manager.rentCar(car.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("not available");
    }

    @Test
    void testRentCarThatDoesNotExistThrows() {
        when(repository.findById(999)).thenReturn(null);
        assertThatThrownBy(() -> manager.rentCar(999))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("999");
    }

    // ── TESTS FOR: returnCar() ─────────────────────────────────────────

    @Test
    void testReturnCarMakesCarAvailableAgain() {
        Car car = new Car("Toyota", "Corolla");
        car.setAvailable(false);
        when(repository.findById(car.getId())).thenReturn(car);
        manager.returnCar(car.getId());
        assertThat(car.isAvailable()).isTrue();
    }

    @Test
    void testReturnCarSavesToRepository() {
        Car car = new Car("Toyota", "Corolla");
        car.setAvailable(false);
        when(repository.findById(car.getId())).thenReturn(car);
        manager.returnCar(car.getId());
        verify(repository).save(car);
    }

    @Test
    void testReturnCarThatWasNeverRentedThrows() {
        Car car = new Car("Toyota", "Corolla");
        when(repository.findById(car.getId())).thenReturn(car);
        assertThatThrownBy(() -> manager.returnCar(car.getId()))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("already available");
    }

    @Test
    void testReturnCarThatDoesNotExistThrows() {
        when(repository.findById(999)).thenReturn(null);
        assertThatThrownBy(() -> manager.returnCar(999))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("999");
    }
}