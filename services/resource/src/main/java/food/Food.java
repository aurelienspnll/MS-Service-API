package food;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Food
{
    @MongoObjectId
    String _id;

    private String name;
    private String description;
    private double price;

    public Food(JSONObject data) {
        this.name = data.getString("name");
        this.description = data.getString("description");
        this.price = data.getDouble("price");
    }

    public Food() {}

    JSONObject toJson() {
        return new JSONObject()
                .put("id", _id)
                .put("name", name)
                .put("description", description)
                .put("price", price);
    }
}
