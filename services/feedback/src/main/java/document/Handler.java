package document;

import com.mongodb.MongoClient;
import document.models.Feedback;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Handler {

    private static MongoCollection feedbacks = getFeedbacks();

    static JSONObject feedback(JSONObject input) {
        Feedback data = new Feedback(input.getJSONObject("feedback"));
        feedbacks.insert(data);
        return new JSONObject().put("inserted", true).put("feedback",data.toJson());
    }

    /**
     * @param input idFood to consult
     * @return list of all feedback about the food
     */
    static JSONObject consult(JSONObject input) {
        String id = input.getString("id");
        MongoCursor<Feedback> cursor = feedbacks.find().as(Feedback.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            Feedback tmp = cursor.next();
            if(tmp.getFood().getId().equals(id)) {
                array.add(tmp.toJson());
            }
        }
        return new JSONObject().put("feedbacks", array);
    }

    static JSONObject list() {
        MongoCursor<Feedback> cursor = feedbacks.find().as(Feedback.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            array.add(cursor.next().toJson());
        }
        return new JSONObject().put("feedbacks", array);
    }

    static JSONObject delete(JSONObject input) {
        String id = input.getString("id");
        Feedback theOne = feedbacks.findOne("{id:#}",id).as(Feedback.class);
        if (null == theOne) {
            return new JSONObject().put("deleted", false);
        }
        feedbacks.remove(new ObjectId(theOne.get_id()));
        return new JSONObject().put("deleted", true);
    }

    static JSONObject deleteAll() {
        feedbacks.drop();
        return new JSONObject().put("allDeleted", true);
    }

    private static MongoCollection getFeedbacks() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }

}