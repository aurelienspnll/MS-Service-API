package scenarios;

import cucumber.api.java.en.*;
import org.json.JSONObject;

import javax.ws.rs.core.MediaType;
import org.apache.cxf.jaxrs.client.WebClient;
import static org.junit.Assert.*;


public class DeliveryDefinition {

    private String host = "localhost";
    private int port = 9100;

    private JSONObject delivery;
    private JSONObject answer;

    private JSONObject call(JSONObject request) {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/delivery-service-document/delivery")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class);
        return new JSONObject(raw);
    }

    @Given("^an empty delivery deployed on (.*):(\\d+)$")
    public void initialize_port(String host, int port) {
        this.host = host;
        this.port = port;
        JSONObject ans = call(new JSONObject().put("event", "CLEAN"));
        assertTrue(ans.getBoolean("allDeleted"));
    }

    @Given("^a delivery with id (.*) added to the database")
    public void preregistered_delivery(String id) {
        JSONObject delivery = new JSONObject();
        delivery.put("id", id);
        delivery.put("deliveryMan", "Escobar");
        delivery.put("idOrder", "56");
        delivery.put("delivered", false);
        JSONObject ans = call(new JSONObject().put("event", "DELIVER").put("delivery", delivery));
        assertEquals(true, ans.getBoolean("inserted"));
    }

    @Given("^a delivery with id (\\d+)$")
    public void initialize_delivery(String id) {
        delivery = new JSONObject();
        delivery.put("id", id);
        delivery.put("delivered", false);
    }

    @Given("^is assigned to (.*)$")
    public void add_deliveryMan(String deliveryMan) { delivery.put("deliveryMan", deliveryMan); }

    @Given("^id order is (\\d+)$")
    public void add_idOrder(String idOrder) { delivery.put("idOrder", idOrder); }

    @When("^the (.*) action is run$")
    public void call_registry(String message) {
        JSONObject request = new JSONObject();
        request.put("event", message);
        switch(message) {
            case "DELIVER":
                request.put("delivery", delivery);
                break;

            default:
                throw new RuntimeException("Unknown message");
        }
        answer = call(request);
        assertNotNull(answer);
    }

    @Then("^the delivery is registered$")
    public void the_delivery_is_registered() {
        assertEquals(true,answer.getBoolean("inserted"));
    }

    @Then("^field (.*) is equals to (.*)$")
    public void check_delivery_content(String key, String value) {
        Object data = answer.getJSONObject("delivery").get(key.trim());
        if(data.getClass().equals(Boolean.class)) {
            assertEquals(Boolean.parseBoolean(value.trim()), data);
        } else {
            assertEquals(value.trim(), data);
        }
    }
}
