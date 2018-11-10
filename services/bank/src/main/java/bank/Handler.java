package bank;

import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;


class Handler {
    static MongoCollection payments = getPayments();


    static JSONObject addPayment(JSONObject input) {
        Payment data = new Payment(input.getJSONObject("payment"));
        payments.insert(data);
        return new JSONObject().put("inserted", true).put("payment",data.toJson());
    }

    static JSONObject validate(JSONObject input)
    {
        String id = input.getString("id");
        Boolean validate = input.getBoolean("validate");
        if(validate) {
            payments.update("{id:#}", id).with("{$set: {'status': 'SUCCESSFUL'}}");
        }else {
            payments.update("{id:#}", id).with("{$set: {'status': 'NOT SUCCESSFUL'}}");
        }
        Payment myPayment = payments.findOne("{id:#}",id).as(Payment.class);
        return new JSONObject().put("approved", validate).put("payment", myPayment.toJson());
    }

    static JSONObject consult(JSONObject input) {
        MongoCursor<Payment> cursor = payments.find().as(Payment.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            array.add(cursor.next().toJson());
        }
        return new JSONObject().put("payments", array);
    }

    private static MongoCollection getPayments() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }
}
