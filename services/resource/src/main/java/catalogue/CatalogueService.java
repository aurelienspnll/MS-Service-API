package catalogue;

import com.mongodb.MongoClient;
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
    public Response getClichedMessage() {
        MongoCollection meals = getMeals();
        MongoCursor<Meal> cursor = meals.find().as(Meal.class);
        JSONArray array = new JSONArray();
        while(cursor.hasNext()) {
            array.put(cursor.next().toJson());
        }
        return Response.ok().entity(array.toString()).build();
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
}
