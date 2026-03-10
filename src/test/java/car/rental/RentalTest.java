package car.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RentalTest {

    @BeforeEach
    void resetCounters() {
        Car.resetIdCounter();
        Rental.resetIdCounter();
    }

    // STEP 1: a new Rental is not null
    @Test
    void rentalIsNotNull() {
        Car car = new Car("Toyota", "Corolla");
        Rental rental = new Rental(car, "Alice");
        assertThat(rental).isNotNull();
    }

    // STEP 2: rental has an id
    @Test
    void rentalHasAnId() {
        Car car = new Car("Toyota", "Corolla");
        Rental rental = new Rental(car, "Alice");
        assertThat(rental.getId()).isEqualTo(1);
    }

    // STEP 3: rental stores the car
    @Test
    void rentalStoresCar() {
        Car car = new Car("Toyota", "Corolla");
        Rental rental = new Rental(car, "Alice");
        assertThat(rental.getCar()).isEqualTo(car);
    }

    // STEP 4: rental stores customer name
    @Test
    void rentalStoresCustomerName() {
        Car car = new Car("Toyota", "Corolla");
        Rental rental = new Rental(car, "Alice");
        assertThat(rental.getCustomerName()).isEqualTo("Alice");
    }

    // STEP 5: two rentals have different ids
    @Test
    void twoRentalsHaveDifferentIds() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        Rental r1 = new Rental(car1, "Alice");
        Rental r2 = new Rental(car2, "Bob");
        assertThat(r1.getId()).isNotEqualTo(r2.getId());
    }
}