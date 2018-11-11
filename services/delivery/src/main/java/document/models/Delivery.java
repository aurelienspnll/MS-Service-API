package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Delivery {

    private String id;
    private Order order;
    private DeliveryMan deliveryMan;
    private Boolean delivered;
    private Boolean deliverable;

    @MongoObjectId
    private
    String _id;

    public Delivery(JSONObject data) {
        this.setId(data.getString("id"));
        this.setOrder(new Order(data.getJSONObject("order")));
        this.setDeliveryMan(new DeliveryMan(data.getJSONObject("deliveryMan")));
        this.setDelivered(data.getBoolean("delivered"));
        this.setDeliverable(true);
    }

    public Delivery() {}

    public JSONObject toJson() {
        return new JSONObject()
                .put("id", getId())
                .put("idOrder", getOrder().toJson())
                .put("deliveryMan", getDeliveryMan().toJson())
                .put("delivered", getDelivered())
                .put("deliverable", getDeliverable());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public DeliveryMan getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(DeliveryMan deliveryMan) {
        this.deliveryMan = deliveryMan;
    }

    public Boolean getDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Boolean getDeliverable() {
        return deliverable;
    }

    public void setDeliverable(Boolean deliverable) {
        this.deliverable = deliverable;
    }
}
