package resource;

import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Random;

@Path("/CalculateEta")
public class CalculateEtaService {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response calculateEta(String input) {
        Random rand = new Random();
        int min = 20;
        int max = 120;
        double delivery = min + rand.nextInt(max - min + 1);
        try {
            return Response.ok().entity(delivery+" s").build();
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString()).build();
        }
    }

}
