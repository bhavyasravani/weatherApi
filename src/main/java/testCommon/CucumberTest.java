package main.java.testCommon;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.junit.Cucumber;
import org.junit.runner.RunWith;


@CucumberOptions(
        plugin = {"pretty"},
        glue = {"main.java.stepDefinitions"},
        features = {"src/main/resources/features"})
@RunWith(Cucumber.class)
public class CucumberTest {


}
