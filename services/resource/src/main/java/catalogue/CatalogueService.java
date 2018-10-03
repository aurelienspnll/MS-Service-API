package catalogue;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.jongo.Jongo;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import org.jongo.MongoCollection;
import org.jongo.MongoCursor;


@Path("/meals")
public class CatalogueService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllMeals(@QueryParam("maxPrice") String maxPrice) {
        if(maxPrice == null){
            maxPrice = "1000";
        }
        MongoCollection meals = getMeals();
        MongoCursor<Meal> cursor = meals.find().as(Meal.class);
        JSONArray array = new JSONArray();
        while(cursor.hasNext()) {
            array.put(cursor.next().toJson());
        }
        return Response.ok().entity(filterMealsByPrice(array,maxPrice).toString()).build();
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        try {
            MongoCollection meals = getMeals();
            Meal data = new Meal(new JSONObject(input));
            meals.insert(data);
            return Response.ok().build();
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString(2)).build();
        }
    }

    private static MongoCollection getMeals() {
        MongoClient client = new MongoClient(Network.HOST, Network.PORT);
        return new Jongo(client.getDB(Network.DATABASE)).getCollection(Network.COLLECTION);
    }


    private JSONArray filterMealsByPrice(JSONArray array,String maxPrice){
        JSONArray filteredMeals = new JSONArray();
        JSONObject object;
        String objectPrice;
        for(int i = 0; i< array.length(); i++){
            object = (JSONObject) array.get(i);
            objectPrice = object.getString("price");
            if( Double.parseDouble(objectPrice) <= Double.parseDouble(maxPrice) ){
                filteredMeals.put(object);
            }
        }
        return filteredMeals;
    }
}
