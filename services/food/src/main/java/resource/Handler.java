package resource;

import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONObject;

public class Handler {

    private static MongoCollection foodList = getAllFood();

    static JSONObject getFoodList(String cat)
    {
        MongoCursor<Food> cursor;
        if(cat != null) {
            cursor = foodList.find("{category:#}", cat.toLowerCase()).as(Food.class);
        } else {
            cursor = foodList.find().as(Food.class);
        }
        JSONArray contents = new JSONArray();
        while(cursor.hasNext()) {
            contents.put(cursor.next().toJson());
        }
        return new JSONObject().put("foods", contents);
    }


    static JSONObject getFood(String foodId)
    {
        Food f = foodList.findOne("{_id:#}", new ObjectId(foodId)).as(Food.class);
        if (f == null) {
            throw new RuntimeException("No match found for " + foodId);
        }
        return f.toJson();
    }


    static JSONObject insertFood(JSONObject input) {
        Food data = new Food(input.getJSONObject("food"));
        foodList.insert(data);
        return new JSONObject().put("inserted", true).put("food",data.toJson());
    }


    static void deleteFoodList() {
        foodList.drop();
    }


    private static MongoCollection getAllFood() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }
}
