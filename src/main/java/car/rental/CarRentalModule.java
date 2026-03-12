package car.rental;

import com.google.inject.AbstractModule;


public class CarRentalModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CarRepository.class).to(InMemoryCarRepository.class);
    }
}