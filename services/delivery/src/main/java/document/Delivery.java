package document;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.util.Random;

public class Delivery {

    private String id;
    private String idOrder;
    private String deliveryMan;
    private Boolean delivered;


    @MongoObjectId
    String _id;

    public Delivery(JSONObject data) {
        this.id = data.getString("id");
        this.idOrder = data.getString(("idOrder"));
        this.deliveryMan = data.getString("deliveryMan");
        this.delivered = data.getBoolean("delivered");
    }

    public Delivery() {} // ne pas enlever, c'est pour instancier la class avec findOne(...OrderFood.class)


    JSONObject toJson() {
        return new JSONObject()
                .put("id", id)
                .put("idOrder", idOrder)
                .put("deliveryMan", deliveryMan)
                .put("delivered", delivered);
    }
}
