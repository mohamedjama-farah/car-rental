package car.rental;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import static org.assertj.core.api.Assertions.assertThat;


public class CarRentalSteps {

    private CarRentalManager manager;
    private Car lastCar;

    @Before
    public void setUp() {
        Car.resetIdCounter();
        Rental.resetIdCounter();
        manager = new CarRentalManager(new InMemoryCarRepository());
    }

    @Given("the car rental manager is empty")
    public void theCarRentalManagerIsEmpty() {
        // manager is already empty from setUp()
    }

    @Given("the car rental manager has a car with make {string} and model {string}")
    public void theManagerHasACar(String make, String model) {
        lastCar = manager.addNewCar(make, model);
    }

    @Given("the car rental manager has a rented car with make {string} and model {string}")
    public void theManagerHasARentedCar(String make, String model) {
        lastCar = manager.addNewCar(make, model);
        manager.rentCar(lastCar.getId());
    }

    @When("I add a new car with make {string} and model {string}")
    public void iAddANewCar(String make, String model) {
        lastCar = manager.addNewCar(make, model);
    }

    @When("I rent the car")
    public void iRentTheCar() {
        manager.rentCar(lastCar.getId());
    }

    @When("I return the car")
    public void iReturnTheCar() {
        manager.returnCar(lastCar.getId());
    }

    @Then("the list of cars should have {int} car")
    public void theListShouldHave(int count) {
        assertThat(manager.getAllCars()).hasSize(count);
    }

    @Then("the car should be unavailable")
    public void theCarShouldBeUnavailable() {
        assertThat(manager.getAllCars().get(0).isAvailable()).isFalse();
    }

    @Then("the car should be available")
    public void theCarShouldBeAvailable() {
        assertThat(manager.getAllCars().get(0).isAvailable()).isTrue();
    }
}