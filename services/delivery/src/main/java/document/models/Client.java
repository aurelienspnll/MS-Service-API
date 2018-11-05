package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Client {

    private String firstName;
    private String lastName;
    private String address;

    @MongoObjectId
    String _id;

    public Client() {
    }

    public Client(JSONObject data) {
        this.firstName = data.getString("firstName");
        this.lastName = data.getString("lastName");
        this.address = data.getString("address");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("address", address);
    }
}

