package scenarios;

import cucumber.api.java.en.Given;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONObject;

import static org.junit.Assert.assertEquals;

public class DeliveryDefinition {

    private String host = "localhost";
    private int port = 8090;

    private JSONObject delivery;
    private JSONObject answer;

    private JSONObject callGet() {
        String raw =
                WebClient.create("http://" + host + ":" + port + "/delivery-service-document/delivery")
                        .get(String.class);
        return new JSONObject(raw);
    }

    @Given("^an empty delivery catalogue deployed on (.*):(\\d+)$")
    public void initialize_port(String host, int port) {
        this.host = host;
        this.port = port;
        int result = WebClient.create("http://" + host + ":" + port + "/delivery-service-document/delivery").delete().getStatus();
        assertEquals(200, result);
    }

    @Given("^The delivery (\\d+)$")
    public void initialize_delivery(String id) { delivery = new JSONObject(); delivery.put("id", id); }

    @Given("^is assigned to (.*)$")
    public void add_deliveryMan(String deliveryMan) { delivery.put("deliveryMan", deliveryMan); }

    @Given("^to deliver the order (\\d+)$")
    public void add_idOrder(String idOrder) { delivery.put("idOrder", idOrder); }

}
