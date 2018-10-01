package catalogue;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Meal
{
    @MongoObjectId
    String _id;

    private String name;
    private String description;
    private String price;

    public Meal(JSONObject data) {
        this.name = data.getString("name");
        this.description = data.getString("description");
        this.price = data.getString("price");
    }

    public Meal() {} // ne pas enlever, c'est pour instancier la class avec findOne(...OrderFood.class)

    JSONObject toJson() {
        return new JSONObject()
                .put("id", _id)
                .put("name", name)
                .put("description", description)
                .put("price", price);
    }
}
