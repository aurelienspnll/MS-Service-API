package delivery;

import orderfood.OrderFood;
import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

import java.util.Random;

public class Delivery {

    private String id;
    private OrderFood order;
    private String deliveryMan;


    @MongoObjectId
    String _id;

    public Delivery(JSONObject data) {
        this.id = data.getString("id");
        this.order = new OrderFood(data.getJSONObject("order"));
        this.deliveryMan = data.getString("deliveryMan");
    }

    public Delivery() {} // ne pas enlever, c'est pour instancier la class avec findOne(...OrderFood.class)


    JSONObject toJson() {
        return new JSONObject()
                .put("id", id)
                .put("order", this.order.toJson());
    }
}
