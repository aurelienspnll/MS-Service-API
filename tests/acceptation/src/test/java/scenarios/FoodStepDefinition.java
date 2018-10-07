package scenarios;

import cucumber.api.java.en.*;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import static org.junit.Assert.*;


public class FoodStepDefinition {

    private String host = "localhost";
    private int port = 8090;

    private JSONObject food;
    private JSONObject answer;

    private JSONObject callGet() {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/food-service-rest/foods")
                        .get(String.class);
        return new JSONObject(raw);
    }

    private JSONObject callPost(JSONObject request) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/food-service-rest/foods")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }


    @Given("^an empty food catalogue deployed on (.*):(\\d+)$")
    public void initialize_port(String host, int port) {
        this.host = host;
        this.port = port;
        int result = WebClient.create("http://" + host + ":" + port + "/food-service-rest/foods").delete().getStatus();
        assertEquals(200, result);
    }

    @Given("^a food named (Cheese Burger|Nachos) added to the catalogue$")
    public void upload_preregistered_food(String name) {
        JSONObject item = new JSONObject();
        if (name.equals("Cheese Burger")) {
            item.put("name", "Cheese Burger")
                    .put("description", "Beef patty topped with a simple layer of melted American cheese, crinkle cut pickles, yellow mustard, and ketchup on a toasted sesame seed bun.")
                    .put("price", "8.60");
        } else {
            item.put("name", "Nachos")
                    .put("description", "Nachos topped with smoky bacon, and plenty of melted cheese.")
                    .put("price", "4.20");
        }
        JSONObject response = callPost(new JSONObject().put("food", item));
        assertEquals(true, response.getBoolean("inserted"));
    }


    @Given("^A food named (.*)$")
    public void initialize_a_food(String foodName) { food = new JSONObject(); food.put("name", foodName); }


    @Given("^with (.*) set to (.*)$")
    public void add_food_attribute(String key, String value) { food.put(key.trim(),value);  }


    @When("^the (.*) method is used$")
    public void call_food_service(String method) {
        switch(method) {
            case "get":
                answer = callGet(); break;
            case "post":
                answer = callPost(new JSONObject().put("food", food)); break;
            default:
                answer = null;
                throw new RuntimeException("Unknown message");
        }
        assertNotNull(answer);
    }

    @Then("^the food is registered$")
    public void food_is_registered() {
        assertEquals(true,answer.getBoolean("inserted"));
    }


    @Then("^the (.*) is equals to (.*)$")
    public void check_food_content(String key, String value) {
        Object data = answer.getJSONObject("food").get(key.trim());
        if(data.getClass().equals(Double.class)) {
            assertEquals(Double.parseDouble(value.trim()), data);
        } else {
            assertEquals(value.trim(), data);
        }
    }

    @Then("^there are (\\d+) food items in the catalogue$")
    public void how_many_items_in_catalogue(int expected) {
        call_food_service("get");
        size_is_good(expected);
    }

    @Then("^the answer contains (\\d+) result(?:s)?$")
    public void size_is_good(int expected) {
        assertEquals(expected, answer.getJSONArray("foods").length());
    }
}
