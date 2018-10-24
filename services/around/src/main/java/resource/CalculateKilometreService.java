package resource;

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
        try {
            return Response.ok().entity(delivery+" km").build();
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString()).build();
        }
    }

}
