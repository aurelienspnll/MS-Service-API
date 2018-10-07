package document;

import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Handler {

    private static MongoCollection deliveries = getDeliveries();


    static JSONObject deliver(JSONObject input) {
        Delivery data = new Delivery(input.getJSONObject("delivery"));
        deliveries.insert(data);
        return new JSONObject().put("inserted", true).put("delivery",data.toJson());
    }

    static JSONObject complete(JSONObject input) {
        String id = input.getString("id");
        Delivery theOne = deliveries.findOne("{id:#}", id).as(Delivery.class);
        if (null == theOne) {
            return new JSONObject().put("completed", false);
        }
        deliveries.update("{id:#}", id).with("{$set: {'delivered': 'true'}}");
        return new JSONObject().put("completed", true).put("delivery", theOne.toJson());
    }

    static JSONObject consult(JSONObject input) {
        String id = input.getString("id");
        Delivery theOne = deliveries.findOne("{id:#}",id).as(Delivery.class);
        if (null == theOne) {
            return new JSONObject().put("consult", false);
        }
        return new JSONObject().put("consult", theOne.toJson());
    }

    static JSONObject list(JSONObject input) {
        MongoCursor<Delivery> cursor = deliveries.find().as(Delivery.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            array.add(cursor.next().toJson());
        }
        return new JSONObject().put("deliveries", array);
    }

    static JSONObject listCompleted(JSONObject input) {
        MongoCursor<Delivery> cursor = deliveries.find("{delivered:true}").as(Delivery.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            array.add(cursor.next().toJson());
        }
        return new JSONObject().put("deliveries", array);
    }

    static JSONObject listNotCompleted(JSONObject input) {
        MongoCursor<Delivery> cursor = deliveries.find("{delivered:false}").as(Delivery.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            array.add(cursor.next().toJson());
        }
        return new JSONObject().put("deliveries", array);
    }

    static JSONObject delete(JSONObject input) {
        String id = input.getString("id");
        Delivery theOne = deliveries.findOne("{id:#}",id).as(Delivery.class);
        if (null == theOne) {
            return new JSONObject().put("deleted", false);
        }
        deliveries.remove(new ObjectId(theOne._id));
        return new JSONObject().put("deleted", true);
    }

    static JSONObject deleteAll() {
        deliveries.drop();
        return new JSONObject().put("allDeleted", true);
    }

    private static MongoCollection getDeliveries() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }
}
