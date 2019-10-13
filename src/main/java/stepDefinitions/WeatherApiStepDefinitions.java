package main.java.stepDefinitions;

import io.cucumber.core.logging.Logger;
import io.cucumber.core.logging.LoggerFactory;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Assert;

import java.time.ZoneId;
import java.time.*;


import static io.restassured.RestAssured.given;

public class WeatherApiStepDefinitions {

    private Response response;
    private JsonPath jsonPath;
    private RequestSpecification request;
    public String CONTEXT_PATH = "https://openweathermap.org/data/2.5/weather";

    public static final Logger logger = LoggerFactory.getLogger(WeatherApiStepDefinitions.class);

    public String jsonBody, requiredResponse;
    public JSONObject jsonObj;

    @Given("I like to holiday in \"([^\"]*)\"")
    public void likeToHolidayInACity(String cityId) throws JSONException {

        Response response = given()
                .queryParam("id", 2154855)
                .queryParam("cnt", 7)
                .when()
                .get("https://openweathermap.org/data/2.5/forecast/daily?appid=b6907d289e10d714a6e88b30761fae22");
        jsonBody = response.getBody().asString();
        jsonObj = new JSONObject(jsonBody);
        String cityName = jsonObj.getJSONObject("city").get("name").toString();
        Assert.assertEquals(cityId, cityName);

    }

    @Given("I only like to holiday on \"([^\"]*)\"")
    public void holidayOnMondayOnly(String dayOfHoliday) throws JSONException {
        // Write code here that turns the phrase above into concrete actions
        JSONArray arrayOfTemp = jsonObj.getJSONArray("list");
        for (int i = 0; i < arrayOfTemp.length(); i++) {
            String dateEpoch = arrayOfTemp.getJSONObject(i).getString("dt");
            DayOfWeek dayOfWeek = ZonedDateTime.ofInstant(Instant.ofEpochMilli(Long.parseLong(dateEpoch)), ZoneId.of("GMT+11")).getDayOfWeek();
            if (dayOfWeek.toString().equals(dayOfHoliday)) {
                requiredResponse = arrayOfTemp.getJSONObject(i).toString();
                break;
            }

        }
    }

    @When("I look up the weather forecast")
    public void i_look_up_the_weather_forecast() {
        // Write code here that turns the phrase above into concrete actions
    }

    @Then("I receive the weather forecast")
    public void i_receive_the_weather_forecast() {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("This is the response body " + requiredResponse);
    }

    @Then("the temperature is warmer than {int} degrees")
    public void the_temperature_is_warmer_than_degrees(Integer int1) throws JSONException {
        // Write code here that turns the phrase above into concrete actions
        String temperature = new JSONObject(requiredResponse).getJSONObject("temp").get("min").toString();
        float temp = Float.parseFloat(temperature);
        Assert.assertTrue(temp >= 10);
    }

}
