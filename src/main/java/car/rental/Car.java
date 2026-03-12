package car.rental;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Car {

    
    private static final Logger LOGGER = LogManager.getLogger(Car.class);

   
    private static int lastId = 0;

    private int     id;
    private String  make;
    private String  model;
    private boolean available;

    public Car(String make, String model) {
        this.id        = ++lastId;
        this.make      = make;
        this.model     = model;
        this.available = true;
        
        LOGGER.info("Created car: id={} {} {}", this.id, make, model);
    }

    public int     getId()       { return id;        }
    public String  getMake()     { return make;      }
    public String  getModel()    { return model;     }
    public boolean isAvailable() { return available; }

    void setAvailable(boolean available) {
        this.available = available;
    }

    static void resetIdCounter() {
        lastId = 0;
    }

    
    public String toString() {
        return id + " - " + make + " " + model
               + " [" + (available ? "available" : "rented") + "]";
    }
}