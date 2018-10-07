package food;

import org.json.JSONObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


import static food.Handler.*;

@Path("/foods")
public class FoodService {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllFood() {
        try {
            return Response.ok().entity(getFoodList().toString()).build();
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString()).build();
        }
    }


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addFood(String input) {
        JSONObject obj = new JSONObject(input);
        try {
            return Response.ok().entity(insertFood(obj).toString()).build();
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString()).build();
        }
    }

    @DELETE
    public Response deleteAllFood() {
        try {
            deleteFoodList();
            return Response.ok().build();
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString()).build();
        }
    }
}
