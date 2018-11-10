package document.models;

import org.json.JSONObject;

public class DeliveryMan {

    private String id;
    private String firstName;
    private String lastName;

    public DeliveryMan() {
    }

    public DeliveryMan(JSONObject data) {
        this.id = data.getString("id");
        this.firstName = data.getString("firstName");
        this.lastName = data.getString("lastName");
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("id", id)
                .put("firstName", firstName)
                .put("lastName", lastName);
    }
}
