package car.rental;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.NoSuchElementException;


public class CarSwingApp extends JFrame {

    private static final long serialVersionUID = 1L;

    // --- widgets ---
    private final JTextField textMake;
    private final JTextField textModel;
    private final JButton btnAdd;
    private final JButton btnRent;
    private final JButton btnReturn;
    private final JList<String> listCars;
    private final DefaultListModel<String> listModel;
    private final JLabel labelError;

    private final CarRentalManager manager;

    public CarSwingApp(CarRentalManager manager) {
        this.manager = manager;

        setTitle("Car Rental");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLayout(new BorderLayout());

        // --- top panel: input fields ---
        JPanel topPanel = new JPanel(new FlowLayout());

        JLabel lblMake = new JLabel("Make:");
        textMake = new JTextField(10);
        textMake.setName("textMake");

        JLabel lblModel = new JLabel("Model:");
        textModel = new JTextField(10);
        textModel.setName("textModel");

        btnAdd = new JButton("Add Car");
        btnAdd.setName("btnAdd");

        topPanel.add(lblMake);
        topPanel.add(textMake);
        topPanel.add(lblModel);
        topPanel.add(textModel);
        topPanel.add(btnAdd);

        // --- center panel: list of cars ---
        listModel = new DefaultListModel<>();
        listCars = new JList<>(listModel);
        listCars.setName("listCars");
        listCars.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(listCars);

        // --- bottom panel: rent/return buttons + error label ---
        JPanel bottomPanel = new JPanel(new FlowLayout());

        btnRent = new JButton("Rent Car");
        btnRent.setName("btnRent");

        btnReturn = new JButton("Return Car");
        btnReturn.setName("btnReturn");

        labelError = new JLabel(" ");
        labelError.setName("labelError");
        labelError.setForeground(Color.RED);

        bottomPanel.add(btnRent);
        bottomPanel.add(btnReturn);
        bottomPanel.add(labelError);

        // --- add panels to frame ---
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // --- button actions ---
        btnAdd.addActionListener(e -> onAddCar());
        btnRent.addActionListener(e -> onRentCar());
        btnReturn.addActionListener(e -> onReturnCar());

        // load existing cars
        refreshCarList();
    }

    private void onAddCar() {
        String make = textMake.getText().trim();
        String model = textModel.getText().trim();
        if (make.isEmpty() || model.isEmpty()) {
            labelError.setText("Make and model are required");
            return;
        }
        manager.addNewCar(make, model);
        textMake.setText("");
        textModel.setText("");
        labelError.setText(" ");
        refreshCarList();
    }

    private void onRentCar() {
        String selected = listCars.getSelectedValue();
        if (selected == null) {
            labelError.setText("No car selected");
            return;
        }
        int id = extractId(selected);
        try {
            manager.rentCar(id);
            labelError.setText(" ");
            refreshCarList();
        } catch (IllegalStateException | NoSuchElementException ex) {
            labelError.setText(ex.getMessage());
        }
    }

    private void onReturnCar() {
        String selected = listCars.getSelectedValue();
        if (selected == null) {
            labelError.setText("No car selected");
            return;
        }
        int id = extractId(selected);
        try {
            manager.returnCar(id);
            labelError.setText(" ");
            refreshCarList();
        } catch (IllegalStateException | NoSuchElementException ex) {
            labelError.setText(ex.getMessage());
        }
    }

    private void refreshCarList() {
        listModel.clear();
        List<Car> cars = manager.getAllCars();
        for (Car car : cars) {
            listModel.addElement(
                car.getId() + " - " + car.getMake() + " " + car.getModel()
                + (car.isAvailable() ? " [available]" : " [rented]")
            );
        }
    }

    private int extractId(String listEntry) {
        // format: "1 - Toyota Corolla [available]"
        return Integer.parseInt(listEntry.split(" - ")[0].trim());
    }
}