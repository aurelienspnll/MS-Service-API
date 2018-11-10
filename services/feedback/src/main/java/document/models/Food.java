package document.models;

import org.json.JSONObject;

public class Food {

    private String id;
    private String name;
    private String category;

    public Food(JSONObject data) {
        this.setId(data.getString("id"));
        this.name = data.getString("name");
        this.category = data.getString("category");
    }

    public Food() {}

    JSONObject toJson() {
        return new JSONObject()
                .put("id", getId())
                .put("name", name)
                .put("category", category);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}