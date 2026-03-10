package car.rental;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;

/**
 * LOCATION: src/test/java/car/rental/CucumberIT.java
 *
 * This class runs all Cucumber scenarios as integration tests.
 * It is named *IT so Failsafe picks it up (mvn verify).
 *
 * @Suite — marks this as a JUnit Platform Suite
 * @IncludeEngines — use the Cucumber engine
 * @SelectClasspathResource — find feature files in src/test/resources/car/rental/
 * @ConfigurationParameter — tell Cucumber where step definitions are
 */
@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("car/rental")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "car.rental")
public class CucumberIT {
    // empty — Cucumber finds and runs all scenarios automatically
}