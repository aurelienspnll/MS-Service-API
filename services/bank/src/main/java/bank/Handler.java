package bank;

import com.mongodb.MongoClient;
import java.util.ArrayList;
import java.util.List;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;
import org.json.JSONObject;


class Handler {

    static JSONObject addpayement(JSONObject input) {
        MongoCollection payements = getPayements();
        payement data = new payement(input.getJSONObject("payement"));
        payements.insert(data);
        return new JSONObject().put("inserted", true).put("payement",data.toJson());
    }

    static JSONObject validate(JSONObject input)
    {
        MongoCollection payements = getPayements();
        String id = input.getString("id");
        Boolean validate = input.getBoolean("validate");
        if(validate) {
            payements.update("{id:#}", id).with("{$set: {'status': 'SUCCESSFUL'}}");
        }else {
            payements.update("{id:#}", id).with("{$set: {'status': 'NOT SUCCESSFUL'}}");
        }
        payement mypayement = payements.findOne("{id:#}",id).as(payement.class);
        return new JSONObject().put("approved", validate).put("payement", mypayement.toJson());
    }

    static JSONObject consult(JSONObject input) {
        MongoCollection payements = getPayements();
        MongoCursor<payement> cursor = payements.find().as(payement.class);
        List array = new ArrayList();
        while(cursor.hasNext()) {
            array.add(cursor.next().toJson());
        }
        return new JSONObject().put("payements", array);
    }


    
    private static MongoCollection getPayements() {
        MongoClient client = new MongoClient("bank-database", 27017);
        return new Jongo(client.getDB("bank")).getCollection("payementService");
    }
}
