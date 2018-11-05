package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Feedback {

    private Client client; //client qui laisse l'avis
    private Food food; //La food sur laquelle on laisse un avis
    private String view; //Le corp de notre avis


    @MongoObjectId
    private String _id;

    public Feedback(JSONObject data) {
    }

    public Feedback() {}


    public JSONObject toJson() {
        return new JSONObject()
                .put("_id", get_id())
                .put("client", getClient().toJson())
                .put("food", getFood().toJson())
                .put("view", getView());
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
