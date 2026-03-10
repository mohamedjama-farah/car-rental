package car.rental;

import org.assertj.swing.edt.GuiActionRunner;
import org.assertj.swing.fixture.FrameFixture;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * LOCATION: src/test/java/car/rental/CarSwingAppIT.java
 *
 * End-to-end UI tests for CarSwingApp.
 * Uses AssertJ Swing to simulate user interactions.
 *
 * Run with: mvn verify
 */
class CarSwingAppIT {

    private FrameFixture window;

    @BeforeEach
    void setUp() {
        Car.resetIdCounter();
        Rental.resetIdCounter();
        CarRentalManager manager = new CarRentalManager(new InMemoryCarRepository());
        CarSwingApp app = GuiActionRunner.execute(
                () -> new CarSwingApp(manager));
        window = new FrameFixture(app);
        window.show();
    }

    @AfterEach
    void tearDown() {
        window.cleanUp();
    }

    @Test
    void testAddCarAppearsInList() {
        window.textBox("textMake").enterText("Toyota");
        window.textBox("textModel").enterText("Corolla");
        window.button("btnAdd").click();
        assertThat(window.list("listCars").contents())
                .anyMatch(s -> s.contains("Toyota") && s.contains("Corolla"));
    }

    @Test
    void testRentCarChangesStatusToRented() {
        window.textBox("textMake").enterText("Toyota");
        window.textBox("textModel").enterText("Corolla");
        window.button("btnAdd").click();
        window.list("listCars").selectItem(0);
        window.button("btnRent").click();
        assertThat(window.list("listCars").contents()[0])
                .contains("[rented]");
    }

    @Test
    void testReturnCarChangesStatusToAvailable() {
        window.textBox("textMake").enterText("Toyota");
        window.textBox("textModel").enterText("Corolla");
        window.button("btnAdd").click();
        window.list("listCars").selectItem(0);
        window.button("btnRent").click();
        window.list("listCars").selectItem(0);
        window.button("btnReturn").click();
        assertThat(window.list("listCars").contents()[0])
                .contains("[available]");
    }

    @Test
    void testAddCarClearsInputFields() {
        window.textBox("textMake").enterText("Toyota");
        window.textBox("textModel").enterText("Corolla");
        window.button("btnAdd").click();
        window.textBox("textMake").requireText("");
        window.textBox("textModel").requireText("");
    }

    @Test
    void testRentWithNoSelectionShowsError() {
        window.button("btnRent").click();
        window.label("labelError").requireText("No car selected");
    }

    @Test
    void testReturnWithNoSelectionShowsError() {
        window.button("btnReturn").click();
        window.label("labelError").requireText("No car selected");
    }
}