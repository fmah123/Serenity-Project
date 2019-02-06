package DarkSkyRequest;

import Step.DarkSkyStep;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SerenityRunner.class)
public class GettingDarkSkyRequest {
    @Steps
    DarkSkyStep darkSkyStep;

    @Test
    public void GetWeatherDataForCoordinates(){
        darkSkyStep.TestSetUp();
        darkSkyStep.GetWeatherData();
        darkSkyStep.RequestSuccessful();
        darkSkyStep.GetDataFromReponse("partly-cloudy-day");
    }

    @Test
    public void ValidateResponseAgainstSchema(){
        darkSkyStep.TestSetUp();
        darkSkyStep.GetWeatherData();
        darkSkyStep.RequestSuccessful();
        darkSkyStep.ValidateJsonSchema();
    }

    @Test
    public void validateTiming(){
        darkSkyStep.TestSetUp();
        darkSkyStep.GetWeatherData();
        darkSkyStep.RequestSuccessful();
        darkSkyStep.CheckTimingLessthan5000();
    }

    @Test
    public void extractDataFromJsonTest(){
        darkSkyStep.TestSetUp();
        darkSkyStep.GetWeatherData();
        darkSkyStep.RequestSuccessful();
        darkSkyStep.ExtractMapOfObjectWithFind();
    }
}
