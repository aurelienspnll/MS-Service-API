package document;

import com.mongodb.MongoClient;
import document.models.Delivery;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.bson.Document;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.set;


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

    static JSONObject list() {
        MongoCursor<Delivery> cursor = deliveries.find().as(Delivery.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            array.add(cursor.next().toJson());
        }
        return new JSONObject().put("deliveries", array);
    }

    static JSONObject listCompleted() {
        MongoCursor<Delivery> cursor = deliveries.find("{delivered:true}").as(Delivery.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            array.add(cursor.next().toJson());
        }
        return new JSONObject().put("deliveries", array);
    }

    static JSONObject listNotCompleted() {
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
        deliveries.remove(new ObjectId(theOne.get_id()));
        return new JSONObject().put("deleted", true);
    }

    static JSONObject deleteAll() {
        deliveries.drop();
        return new JSONObject().put("allDeleted", true);
    }


    /**
     * In the future, this method can estimate how much time is left for the delivery man to arrive
     * @param input id of the delivery man
     * @return random geographic position
     */
    /* VERSION 1 : TRACKING
    static JSONObject tracking(JSONObject input){
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(cal.getTime());
        Random rand = new Random();
        int lat = rand.nextInt(180); //latitude
        int lon = rand.nextInt(180); //longitude
        JSONObject answer = new JSONObject();
        answer.put("realTime", currentTime);
        answer.put("latitude", lat);
        answer.put("longitude", lon);
        return new JSONObject().put("tracking", answer);
    }
    */

    /**
     * Method to track a delivery
     * In the future, this method can estimate how much time is left for the delivery man to arrive
     * @param input id of delivery
     * @return random geographic position
     */
    static JSONObject tracking(JSONObject input){
        String id = input.getString("id");
        Delivery theOne = deliveries.findOne("{id:#}",id).as(Delivery.class);
        if (null == theOne) {
            return new JSONObject().put("tracking", "Delivery not found");
        }
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(cal.getTime());
        Random rand = new Random();
        int lat = rand.nextInt(180); //latitude
        int lon = rand.nextInt(180); //longitude
        JSONObject answer = new JSONObject();
        answer.put("coursier", theOne.getDeliveryMan().getFirstName());
        answer.put("realTime", currentTime);
        answer.put("latitude", lat);
        answer.put("longitude", lon);
        return new JSONObject().put("tracking", answer);
    }

    /**
     * Method to signal a problem for a delivery
     * @param input id of delivery
     * @return
     */
    static JSONObject problem(JSONObject input){
        String id = input.getString("id");
        Delivery theOne = deliveries.findOne("{id:#}",id).as(Delivery.class);
        if (null == theOne) {
            return new JSONObject().put("problem", "Delivery not found");
        }
        theOne.setDeliverable(false);
        deliveries.update("{id:#}", id).with("{$set: {'deliverable': 'false'}}");
        return new JSONObject().put("problem", theOne.toJson());
    }

    private static MongoCollection getDeliveries() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }
}
