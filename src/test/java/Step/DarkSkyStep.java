package Step;

import DarkSkyRequest.DarkSkyApi;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import java.util.List;


import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class DarkSkyStep {
    private Response response;

    @Step
    public void TestSetUp(){
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .setBaseUri("https://api.darksky.net")
                .setBasePath("/forecast/23cb2fc34adc794c581f7a752eb1cd2b").build();

        RestAssured.proxy(8888);

        RestAssured.requestSpecification = requestSpecification;

        ResponseSpecification responseSpecification = new ResponseSpecBuilder().build();
        RestAssured.responseSpecification = responseSpecification;
    }

    @Step("I search for weather data for {0} coordinates ")
    public void GetWeatherData(){
        response = SerenityRest.when().get(DarkSkyApi.Coordinates);
    }

    @Step
    public void RequestSuccessful(){
        response.then().statusCode(200);
    }

    @Step
    public void GetDataFromReponse(String name){
        response.then().body("hourly.icon",is(name));
    }

    @Step
    public void ExtractMapOfObjectWithFind(){
        response.then().body("hourly.data.find{ it.summary == 'Clear'}.icon",is("clear-night"));
    }

    @Step
    public void ValidateJsonSchema(){
        response.then().body(matchesJsonSchemaInClasspath("DarkSkySchema.json"));
    }

    @Step
    public void CheckTimingLessthan5000(){
        response.then().time(lessThan(5000L));
    }

    @Step
    public void ExtractSummaryContents() {
        Response response = given().when().get("/37.8267,-122.4233").then().contentType(ContentType.JSON).extract().response();

        List<String> summaries = response.path("hourly.data.summary");
    }
}
