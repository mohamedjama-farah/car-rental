package car.rental;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.swing.SwingUtilities;

/**
 * LOCATION: src/main/java/car/rental/CarRentalApp.java
 *
 * Main entry point of the application.
 * Uses Guice to create and inject all objects.
 *
 * Guice reads CarRentalModule to know:
 *   CarRepository -> InMemoryCarRepository
 *
 * Then it automatically creates:
 *   CarRentalManager (with InMemoryCarRepository injected)
 *   CarSwingApp (with CarRentalManager injected)
 */
public class CarRentalApp {

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CarRentalModule());
        CarRentalManager manager = injector.getInstance(CarRentalManager.class);
        SwingUtilities.invokeLater(() -> {
            CarSwingApp app = new CarSwingApp(manager);
            app.setVisible(true);
        });
    }
}