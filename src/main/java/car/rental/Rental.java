package car.rental;

public class Rental {

    private static int lastId = 0;

    private int id;
    private Car car;
    private String customerName;

    public Rental(Car car, String customerName) {
        this.id = ++lastId;
        this.car = car;
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public Car getCar() {
        return car;
    }

    public String getCustomerName() {
        return customerName;
    }

    static void resetIdCounter() {
        lastId = 0;
    }
}