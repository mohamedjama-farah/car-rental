package car.rental;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * LOCATION: src/main/java/car/rental/Car.java
 *
 * V2 changes from V1:
 *   - Added a LOGGER using Log4j
 *   - Everything else is identical to V1
 *
 * The LOGGER line:
 *   private static final Logger LOGGER = LogManager.getLogger(Car.class);
 *
 *   static        = one logger shared by ALL Car objects (not one per car)
 *   final         = the logger reference never changes
 *   Car.class     = tells Log4j this logger belongs to the Car class
 *                   so log messages show [Car] as the source
 */
public class Car {

    // ── NEW in V2: logger ──────────────────────────────────────────────
    private static final Logger LOGGER = LogManager.getLogger(Car.class);

    // ── same as V1 ─────────────────────────────────────────────────────
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
        // ── NEW in V2: log when a car is created ───────────────────────
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

    @Override
    public String toString() {
        return id + " - " + make + " " + model
               + " [" + (available ? "available" : "rented") + "]";
    }
}