package document.models;

import org.json.JSONObject;

public class DeliveryMan {

    private String id;
    private String firstName;
    private String lastName;

    public DeliveryMan() {
    }

    public DeliveryMan(JSONObject data) {
        this.setId(data.getString("idDeliveryMan"));
        this.setFirstName(data.getString("firstName"));
        this.setLastName(data.getString("lastName"));
    }


    JSONObject toJson() {
        return new JSONObject()
                .put("idDeliveryMan", getId())
                .put("firstName", getFirstName())
                .put("lastName", getLastName());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
