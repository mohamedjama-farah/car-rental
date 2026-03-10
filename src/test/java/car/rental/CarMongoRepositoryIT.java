package car.rental;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * LOCATION: src/test/java/car/rental/CarMongoRepositoryIT.java
 *
 * Integration tests for CarMongoRepository.
 * Requires a real MongoDB running (via Docker Compose).
 *
 * Run with: mvn verify
 * (make sure MongoDB is running first: docker-compose up -d)
 *
 * On CI: MongoDB is started automatically via docker-compose
 * in the GitHub Actions workflow.
 *
 * Uses environment variables:
 *   MONGO_HOST (default: localhost)
 *   MONGO_PORT (default: 27017)
 */
class CarMongoRepositoryIT {

    private static MongoClient mongoClient;
    private CarMongoRepository repository;

    @BeforeAll
    static void setUpAll() {
        String host = System.getenv("MONGO_HOST") != null
                ? System.getenv("MONGO_HOST") : "localhost";
        String port = System.getenv("MONGO_PORT") != null
                ? System.getenv("MONGO_PORT") : "27017";
        mongoClient = MongoClients.create("mongodb://" + host + ":" + port);
    }

    @AfterAll
    static void tearDownAll() {
        mongoClient.close();
    }

    @BeforeEach
    void setUp() {
        Car.resetIdCounter();
        // drop the collection before each test for a clean state
        mongoClient.getDatabase("car-rental").getCollection("cars").drop();
        repository = new CarMongoRepository(mongoClient);
    }

    @Test
    void testSaveAndFindById() {
        Car car = new Car("Toyota", "Corolla");
        repository.save(car);
        Car found = repository.findById(car.getId());
        assertThat(found).isNotNull();
        assertThat(found.getMake()).isEqualTo("Toyota");
        assertThat(found.getModel()).isEqualTo("Corolla");
    }

    @Test
    void testFindAll() {
        repository.save(new Car("Toyota", "Corolla"));
        repository.save(new Car("Honda", "Civic"));
        List<Car> cars = repository.findAll();
        assertThat(cars).hasSize(2);
    }

    @Test
    void testFindByIdNotFound() {
        Car found = repository.findById(999);
        assertThat(found).isNull();
    }

    @Test
    void testSaveUpdatesExistingCar() {
        Car car = new Car("Toyota", "Corolla");
        repository.save(car);
        // make car unavailable and save again
        car.setAvailable(false);
        repository.save(car);
        Car found = repository.findById(car.getId());
        assertThat(found.isAvailable()).isFalse();
    }

    @Test
    void testFindAllEmpty() {
        List<Car> cars = repository.findAll();
        assertThat(cars).isEmpty();
    }

    @Test
    void testSaveMultipleCars() {
        Car car1 = new Car("Toyota", "Corolla");
        Car car2 = new Car("Honda", "Civic");
        Car car3 = new Car("Ford", "Focus");
        repository.save(car1);
        repository.save(car2);
        repository.save(car3);
        assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    void testAvailableCarIsSavedCorrectly() {
        Car car = new Car("Toyota", "Corolla");
        repository.save(car);
        Car found = repository.findById(car.getId());
        assertThat(found.isAvailable()).isTrue();
    }

    @Test
    void testUnavailableCarIsSavedCorrectly() {
        Car car = new Car("Toyota", "Corolla");
        car.setAvailable(false);
        repository.save(car);
        Car found = repository.findById(car.getId());
        assertThat(found.isAvailable()).isFalse();
    }
}