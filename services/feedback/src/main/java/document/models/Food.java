package document.models;

import org.jongo.marshall.jackson.oid.MongoObjectId;
import org.json.JSONObject;

public class Food {

    @MongoObjectId
    String _id;

    private String name;
    private String category;

    public Food(JSONObject data) {
        this.name = data.getString("name");
        this.category = data.getString("category");
    }

    public Food() {}

    JSONObject toJson() {
        return new JSONObject()
                .put("id", _id)
                .put("name", name)
                .put("category", category);
    }

}