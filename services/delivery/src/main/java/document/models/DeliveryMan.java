package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class DeliveryMan {

    private String firstName;
    private String lastName;

    @MongoObjectId
    String _id;

    public DeliveryMan() {
    }

    public DeliveryMan(JSONObject data) {
        this.firstName = data.getString("firstName");
        this.lastName = data.getString("lastName");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName);
    }
}
