package step_definitions;

import com.fasterxml.jackson.databind.ObjectMapper;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import helpers.Log;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import pageobjects.HomePage;
import utils.users;

import java.util.Map;

import static java.lang.Integer.parseInt;

public class BlankStepDefs {
    public WebDriver driver;
    private Map<String, String> sites;
    private String currentSite;
    HomePage sp;

    public BlankStepDefs() {
        driver = Hooks.driver;
    }

    @Given("^Check API$")
    public void checkAPI() {
        System.out.print("Starting call to API......");
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://jsonplaceholder.typicode.com/todos/1")
                .get()
                .addHeader("Cache-Control", "no-cache")
                .addHeader("Postman-Token", "3ff9eb98-769d-444e-b320-4ed06874a8e6")
                .build();
try {
    Response response = client.newCall(request).execute();

    //System.out.print("\nmessage: "+response.message());
    //System.out.print("\nresponse.body: "+response.body().string());

    String result = response.body().string();
    ObjectMapper myMapper = new ObjectMapper();
    users u  = myMapper.readValue(result, users.class);
    System.out.print("\nDetails of my User:\n    " + u.toString());

}catch (Exception e){
    System.out.print(e.getMessage());
}
    }

    @Given("^I open (.*) webPage$")
    public void iOpenParamwebWebPage(String paramweb) {
        sp = new HomePage(driver);
        sp.loadSearchPage();
    }

    @When("^Search (.*), (.*) for a start value of (.*)$")
    public void searchLocationAndPriceCriteria(String Location,
                                               String Custom, String Price) {

        Log.info("Values: " + Location + "/" + Custom + "/" + Price);
        sp.searchOnPageWithParameters(Location, Custom, Price);

    }

    @Then("^I validate that first element showed is according to criteria (.*)$")
    public void validateFirstOptionValue(String Price) {

        String showedFirstValue = sp.readFirstValue();

        Log.info("\nChecking values: " + parseInt(showedFirstValue) + " >= " + parseInt(Price) + "\n");

        boolean result = parseInt(showedFirstValue) >= parseInt(Price);
        Assert.assertTrue(result);

        Log.info("  - First showed value checked OK!\n");
    }

}