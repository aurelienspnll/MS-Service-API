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

    //order number 44: Hot Wings 30 Bucket at 3 avenue promenade des anglais for Aurélien
    @Given("^a order with id (.*): (.*) at (.*) for (.*)$")
    public void orderMeal(String id,String nameMeal, String adress, String clientName) {
        JSONObject order = new JSONObject();
        order.put("id", id)
                    .put("nameOfFood", nameMeal)
                    .put("nameOfClient", clientName)
                    .put("addressDestination", adress);
        JSONObject ans = call(new JSONObject().put("event", "ORDER").put("orderFood", order));
        assertEquals(true, ans.getBoolean("inserted"));
    }


}
