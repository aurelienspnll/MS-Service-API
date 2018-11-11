package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Order {

    private String id;
    private String idClient;
    private String clientName;
    private String address;

    public Order() {
    }

    public Order(JSONObject data) {
        this.id = data.getString("idOrder");
        this.idClient = data.getString("idClient");
        this.clientName = data.getString("clientName");
        this.address = data.getString("address");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("idOrder", id)
                .put("idClient", idClient)
                .put("firstName", clientName)
                .put("address", address);
    }

}
