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
        //TODO : creation d'un feedback
        return new JSONObject();
    }

    static JSONObject consult(JSONObject input) {
        /*
        TODO : Le consult devra non pas consulter un avis par rapport à son id, mais tous les avis pour un
        TODO : idFood -> permet de savoir tous les avis d'un plat */
        return new JSONObject();
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