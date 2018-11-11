package resource;

import org.json.JSONArray;
import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

@Path("/CalculateKilometre")
public class CalculateKilometreService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response calculateKm(String input) {
        Random rand = new Random();
        int min = 5;
        int max = 100;
        double delivery = min + rand.nextInt(max - min + 1);
        JSONArray array = new JSONArray();
        JSONObject response1 = new JSONObject();
        response1.put("idDelivery","33");
        response1.put("distance",delivery+"km");

        JSONObject response2 = new JSONObject();
        response2.put("idDelivery","2");
        response2.put("distance","1km");

        array.put(response1);
        array.put(response2);

        try {
            return Response.ok().entity(array).build();
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString()).build();
        }
    }

}
