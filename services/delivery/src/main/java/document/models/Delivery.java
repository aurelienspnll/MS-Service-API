package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Delivery {

    private String id;
    private String idOrder;
    private String deliveryMan;
    private Boolean delivered;


    @MongoObjectId
    private
    String _id;

    public Delivery(JSONObject data) {
        this.setId(data.getString("id"));
        this.setIdOrder(data.getString(("idOrder")));
        this.setDeliveryMan(data.getString("deliveryMan"));
        this.setDelivered(data.getBoolean("delivered"));
    }

    public Delivery() {} // ne pas enlever, c'est pour instancier la class avec findOne(...OrderFood.class)


    public JSONObject toJson() {
        return new JSONObject()
                .put("id", getId())
                .put("idOrder", getIdOrder())
                .put("deliveryMan", getDeliveryMan())
                .put("delivered", getDelivered());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(String idOrder) {
        this.idOrder = idOrder;
    }

    public String getDeliveryMan() {
        return deliveryMan;
    }

    public void setDeliveryMan(String deliveryMan) {
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
}
