package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Client {

    @MongoObjectId
    String _id;

    private String firstName;
    private String lastName;

    public Client() {
    }

    public Client(JSONObject data) {
        this.firstName = data.getString("firstName");
        this.lastName = data.getString("lastName");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("firstName", firstName)
                .put("lastName", lastName);
    }
}