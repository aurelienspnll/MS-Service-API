package scenarios;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.apache.cxf.jaxrs.client.WebClient;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.ws.rs.core.MediaType;
import static org.junit.Assert.assertEquals;


public class initialStoryDefinition {

    String nameClient;
    String addressClient;
    JSONArray foodCatalogueFiltered;
    JSONObject foodSelected;
    JSONObject order;
    JSONObject deliver;

    @Given("^a full food catalogue$")
    public void getFoodCatalogue() {

        int result = WebClient.create("http://localhost:9090/food-service-rest/foods").delete().getStatus();

        String asian = "{\n" +
                "\"food\":\n" +
                "{\n" +
                "  \"name\": \"Ramen soup\",\n" +
                "  \"description\": \"Super soup\",\n" +
                "  \"category\": \"asian\",\n" +
                "  \"price\": 5.5\n" +
                "}}";

        JSONObject response = new JSONObject(
                WebClient.create("http://localhost:9090/food-service-rest/foods")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(asian, String.class));

        String mexican = "{\n" +
                "\"food\":\n" +
                "{\n" +
                "  \"name\": \"Tacos\",\n" +
                "  \"description\": \"Tacos\",\n" +
                "  \"category\": \"mexican\",\n" +
                "  \"price\": 5.5\n" +
                "}}";

        JSONObject response2 = new JSONObject(
                WebClient.create("http://localhost:9090/food-service-rest/foods")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(mexican, String.class));
    }



    @Given("^(.*) can browse the food catalogue by categories, so he can immediately identify his favorite junk food - (.*) meals$")
    public void getFoodCatalogue(String client, String category) {
        nameClient = client.trim();

        String result = WebClient.create("http://localhost:9090/food-service-rest/foods").query("cat", category).get(String.class);
        foodCatalogueFiltered = new JSONObject(result).getJSONArray("foods");

        System.out.println("\nService Food - Getting catalogue by category: " + category + "\n");
    }



    @When("^He orders his favorite junk food - (.*) to be deliver to (.*)$")
    public void orderFood(String foodSelectedName, String address)
    {
        addressClient = address.trim();

        // Get food selected
        for (int i = 0; i < foodCatalogueFiltered.length(); i++) {
            JSONObject food = foodCatalogueFiltered.getJSONObject(i);
            if(food.getString("name").toLowerCase().trim().equals(foodSelectedName.toLowerCase().trim())) {
                foodSelected = food;
            }
        }


        // Create the order and place it
        JSONObject orderFood = new JSONObject();
        orderFood.put("id", foodSelected.getString("id"))
                .put("nameOfFood", foodSelected.getString("name"))
                .put("nameOfClient", nameClient)
                .put("addressDestination", addressClient);

        JSONObject request = new JSONObject();
        request.put("event", "ORDER")
                .put("orderFood", orderFood);

        JSONObject response = new JSONObject(
                WebClient.create("http://localhost:9080/order-service-document/ordersFood")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class));

        assertEquals(true, response.getBoolean("inserted"));

        order = response.getJSONObject("orderFood");

        System.out.println("\nService OrderFood - Ordering food: " + foodSelectedName + "\n");



        // Create the delivery
        String request2 = "{\"event\" : \"DELIVER\",\n" +
                "         \"delivery\" : {\n" +
                "            \"id\": \"12\",\n" +
                "            \"order\": {\n" +
                "            \"idOrder\" : \"" + order.getString("id") + "\",\n" +
                "            \"idClient\" : \"1\",\n" +
                "            \"clientName\" : \"" + nameClient + "\",\n" +
                "            \"address\" : \"" + addressClient + "\"\n" +
                "\n" +
                "            },\n" +
                "            \"deliveryMan\": {\n" +
                "            \"idDeliveryMan\" : \"\",\n" +
                "            \"firstName\": \"\",\n" +
                "            \"lastName\" : \"\"\n" +
                "            },\n" +
                "            \"delivered\": false\n" +
                "         }";

        // Create the delivery
       /* JSONObject deliverOrder = new JSONObject();
        deliverOrder.put("deliveryMan", "")
                .put("delivered", false)
                .put("id", "12")
                .put("idOrder", order.getString("id"));

        JSONObject request2 = new JSONObject();
        request2.put("delivery", deliverOrder)
                .put("event", "DELIVER");*/

        JSONObject response2 = new JSONObject(
                WebClient.create("http://localhost:9100/delivery-service-document/delivery")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header(    "Content-Type", MediaType.APPLICATION_JSON)
                        .post(request2, String.class));

        assertEquals(true, response2.getBoolean("inserted"));
    }



    @Then("^(.*), the restaurant chef can access to the order list, so that he can prepare the meal efficiently$")
    public void getOrderList(String chefName)
    {
        // Get all orders to be prepared
        JSONObject request = new JSONObject();
        request.put("event", "CONSULT");

        JSONObject response = new JSONObject(
                WebClient.create("http://localhost:9080/order-service-document/ordersFood")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class));

        JSONArray orderList = response.getJSONArray("orders");

        String id = "";
        for (int i = 0; i < orderList.length(); i++) {
            JSONObject ord = orderList.getJSONObject(i);
            if(ord.getString("id").trim().equals(order.getString("id").trim())) {
                id = ord.getString("id");
            }
        }

        assertEquals(order.getString("id"), id);

        System.out.println("\nService OrderFood - Getting list of orders to prepare\n");
    }



    @Then("^(.*), the coursier can know the orders that will have to be delivered around him, so that he can choose one and go to the restaurant to begin the course$")
    public void getOrderListAround(String deliveryMan)
    {
        // Get list of orders to be delivered
        JSONObject request = new JSONObject();
        request.put("event", "LISTNOTCOMPLETED");

        JSONObject response = new JSONObject(
                WebClient.create("http://localhost:9100/delivery-service-document/delivery")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class));

        JSONArray ordersNotDeliveredList = response.getJSONArray("deliveries");

        for (int i = 0; i < ordersNotDeliveredList.length(); i++) {
            JSONObject ordNotDelivered = ordersNotDeliveredList.getJSONObject(i);
            if(ordNotDelivered.getJSONObject("order").getString("id").trim().equals(order.getString("id").trim())) {
                deliver = ordNotDelivered;
            }
        }
        assertEquals(deliver.getJSONObject("order").getString("id"), order.getString("id"));
        System.out.println("\n Service delivery - Getting orders to be delivered around coursier\n");


        // Coursier choose order to deliver
        /*String request2 = "{\"deliveryMan\": {\n" +
                "            \t\"idDeliveryMan\" : \"2\",\n" +
                "            \t\"firstName\": \"Pablo\",\n" +
                "            \t\"lastName\" : \"Escobar\"\n" +
                "            }}";

        JSONObject request2 = new JSONObject();
        request2.put("event", "ASSIGN")
                .put("id", deliver.getString("id"))
                .put("deliveryMan", deliveryMan);

        JSONObject deliverAssigned = new JSONObject(
                WebClient.create("http://localhost:9100/delivery-service-document/delivery")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request2.toString(), String.class));

        assertEquals(true, deliverAssigned.getBoolean("assigned"));*/

        System.out.println("\n Service delivery - Coursier choosing the order to deliver \n");
    }



    @Then("^he can notify that the order has been delivered, so that his account can be credited and the restaurant can be informed$")
    public void getNotifyOrderDelivered()
    {
        /*JSONObject request = new JSONObject();
        request.put("event", "COMPLETE")
                .put("id", deliver.getString("id"));

        JSONObject response = new JSONObject(
                WebClient.create("http://localhost:9100/delivery-service-document/delivery")
                        .accept(MediaType.APPLICATION_JSON_TYPE)
                        .header("Content-Type", MediaType.APPLICATION_JSON)
                        .post(request.toString(), String.class));

        assertEquals(true, response.getBoolean("completed"));*/

        System.out.println("\nService delivey - Coursier notifying the order has been delivered\n");
    }
}
