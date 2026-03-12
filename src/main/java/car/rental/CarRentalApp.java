package car.rental;

import com.google.inject.Guice;
import com.google.inject.Injector;

import javax.swing.SwingUtilities;


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