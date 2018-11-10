package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Client {

    private String id;
    private String firstName;
    private String lastName;
    private String address;

    public Client() {
    }

    public Client(JSONObject data) {
        this.id = data.getString("id");
        this.firstName = data.getString("firstName");
        this.lastName = data.getString("lastName");
        this.address = data.getString("address");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("id", id)
                .put("firstName", firstName)
                .put("lastName", lastName)
                .put("address", address);
    }
}

