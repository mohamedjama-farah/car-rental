package car.rental;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CarTest {

    @BeforeEach
    void resetCounter() {
        Car.resetIdCounter();
    }

    // ── TESTS FOR: id auto-increment ───────────────────────────────────

    @Test
    void testFirstCarHasIdOne() {
        Car car = new Car("Toyota", "Corolla");
        assertThat(car.getId()).isEqualTo(1);
    }

    @Test
    void testSecondCarHasIdTwo() {
        new Car("Toyota", "Corolla");
        Car second = new Car("Honda", "Civic");
        assertThat(second.getId()).isEqualTo(2);
    }

    @Test
    void testEachCarGetsNextId() {
        Car c1 = new Car("Toyota", "Corolla");
        Car c2 = new Car("Honda",  "Civic");
        assertThat(c2.getId()).isEqualTo(c1.getId() + 1);
    }

    // ── TESTS FOR: constructor stores make and model ───────────────────

    @Test
    void testCarStoresMake() {
        Car car = new Car("Toyota", "Corolla");
        assertThat(car.getMake()).isEqualTo("Toyota");
    }

    @Test
    void testCarStoresModel() {
        Car car = new Car("Toyota", "Corolla");
        assertThat(car.getModel()).isEqualTo("Corolla");
    }

    // ── TESTS FOR: availability ────────────────────────────────────────

    @Test
    void testNewCarIsAvailableByDefault() {
        Car car = new Car("Toyota", "Corolla");
        assertThat(car.isAvailable()).isTrue();
    }

    @Test
    void testCarCanBeSetToUnavailable() {
        Car car = new Car("Toyota", "Corolla");
        car.setAvailable(false);
        assertThat(car.isAvailable()).isFalse();
    }

    @Test
    void testCarCanBeSetBackToAvailable() {
        Car car = new Car("Toyota", "Corolla");
        car.setAvailable(false);
        car.setAvailable(true);
        assertThat(car.isAvailable()).isTrue();
    }

    // ── TESTS FOR: toString ────────────────────────────────────────────

    @Test
    void testToStringAvailable() {
        Car car = new Car("Toyota", "Corolla");
        assertThat(car.toString()).contains("available");
    }

    @Test
    void testToStringRented() {
        Car car = new Car("Toyota", "Corolla");
        car.setAvailable(false);
        assertThat(car.toString()).contains("rented");
    }
}