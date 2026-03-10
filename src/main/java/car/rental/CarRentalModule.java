package car.rental;

import com.google.inject.AbstractModule;

/**
 * LOCATION: src/main/java/car/rental/CarRentalModule.java
 *
 * Guice module — tells Guice which implementation to use
 * for each interface.
 *
 * bind(CarRepository.class).to(InMemoryCarRepository.class)
 * means: whenever someone needs a CarRepository,
 * give them an InMemoryCarRepository.
 */
public class CarRentalModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CarRepository.class).to(InMemoryCarRepository.class);
    }
}