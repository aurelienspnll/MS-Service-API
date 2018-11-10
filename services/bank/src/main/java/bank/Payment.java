package bank;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Payment {

    private Double price;
    private String status;


    @MongoObjectId
    String _id;

    public Payment(JSONObject data) {
        this.price = data.getDouble("price");
        this.status = "Processing";
    }

    public Payment() {} // ne pas enlever, c'est pour instancier la class avec findOne(...payement.class)




    public JSONObject toJson() {
        return new JSONObject()
                .put("id", _id)
                .put("price", price)
                .put("status", status);
    }

}
