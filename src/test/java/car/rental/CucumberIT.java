package car.rental;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.GLUE_PROPERTY_NAME;


@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("car/rental")
@ConfigurationParameter(key = GLUE_PROPERTY_NAME, value = "car.rental")
public class CucumberIT {
    // empty — Cucumber finds and runs all scenarios automatically
}