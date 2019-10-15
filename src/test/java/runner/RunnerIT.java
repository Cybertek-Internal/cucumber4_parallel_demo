package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {
                "json:target/cucumber.json",
                "html:target/default-cucumber-reports",
                "rerun:target/rerun.txt"
        },
        glue = {"step_definitions"},
        features = {
                "src/test/resources/features/topmenu",
                "src/test/resources/features/fleet"
        })
public class RunnerIT {

}
