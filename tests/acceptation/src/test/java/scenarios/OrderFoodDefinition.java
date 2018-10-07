package scenarios;

import cucumber.api.java.en.*;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import static org.junit.Assert.*;

public class OrderFoodDefinition {


    private String host = "host";
    private int port = 9080;

    private JSONObject order;
    private JSONObject answer;
    private String identifiant;
    private boolean validate;

    private JSONObject call(JSONObject request) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/order-service-document/ordersFood")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }

    @Given("^an empty orders deployed on (.*):(\\d+)$")
    public void set_clean_registry(String host, int port) {
        this.host = host;
        this.port = port;
        JSONObject ans = call(new JSONObject().put("event", "PURGE").put("use_with", "caution"));
        assertEquals("done", ans.getString("purge"));
    }

    @Given("^a order with id (.*) added to the registry$")
    public void preregistered_orderMeal(String id) {
        JSONObject order = new JSONObject();
        order.put("id", id).put("nameOfFood", "wings").put("nameOfClient", "jean").put("addressDestination", "22 promenade des anglais");
        JSONObject ans = call(new JSONObject().put("event", "ORDER").put("orderFood", order));
        assertEquals(true, ans.getBoolean("inserted"));
    }

    @Given("^A order identified as (.*)$")
    public void initialize_a_order(String id){
        order = new JSONObject();
        order.put("id",id);
    }

    @Given("^key (.*) set to (.*)$")
    public void add_order_attribute(String key, String value) { order.put(key.trim(),value);  }



    @When("^the (.*) message is sent$")
    public void call_registry(String message) {
        JSONObject request = new JSONObject();
        switch(message) {
            case "ORDER":
                request.put("event", message).put("orderFood", order); break;
            case "VALIDATE":
                request.put("event", message).put("id", identifiant).put("validate", validate); break;
            case "CONSULT":
                request.put("event", message);
                break;
            case "PURGE":
                request.put("event", message); break;
            default:
                throw new RuntimeException("Unknown message");
        }
        answer = call(request);
        assertNotNull(answer);
    }

    @Then("^the order is registered$")
    public void the_order_is_registered() {
        assertEquals(true,answer.getBoolean("inserted"));
    }

    @Then("^key (.*) is equals to (.*)$")
    public void check_order_content(String key, String value) {
        Object data = answer.getJSONObject("orderFood").get(key.trim());
            assertEquals(value.trim(), data);
    }

    @Then("^key (.*) is given$")
    public void check_order_content(String key) {
        Object data = answer.getJSONObject("orderFood").get(key.trim());
        assertNotNull(data);
    }


    @Given("^an id identified as (.*)$")
    public void setIdentifiant(String id) { this.identifiant = id; }


    @Given("^set key validate to (.*)$")
    public void setValidate(String validate) { this.validate = Boolean.parseBoolean(validate); }

    @Then("^order approval is (.*)$")
    public void the_status(String approved) {
        assertEquals(Boolean.parseBoolean(approved),answer.getBoolean("approved"));
    }

    @Then("^there (?:are|is) (\\d+) order(?:s)? in the registry$")
    public void how_many_orders_in_the_registry(int expected) {
        assertEquals(answer.getJSONArray("orders").length(),expected);
    }



}
