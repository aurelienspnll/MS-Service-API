package document;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import static document.Handler.*;

@Path("/document")
@Produces(MediaType.APPLICATION_JSON)
public class Assignation {

    private static final int INDENT_FACTOR = 2;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        JSONObject obj = new JSONObject(input);
        try {
            switch (EVENT.valueOf(obj.getString("event"))) {
                case REGISTER:
                    return Response.ok().entity(delivery(obj).toString(INDENT_FACTOR)).build();
                case LIST:
                    return Response.ok().entity(list(obj).toString(INDENT_FACTOR)).build();
                case DELETE:
                    return Response.ok().entity(delete(obj).toString(INDENT_FACTOR)).build();
            }
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
        return null;
    }

}