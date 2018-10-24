package bank;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import static bank.Handler.*;

@Path("/ordersFood")
@Produces(MediaType.APPLICATION_JSON)
public class payementService {

    private static final int INDENT_FACTOR = 1;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        JSONObject obj = new JSONObject(input);
        try {
            switch (EVENT.valueOf(obj.getString("event"))) {
                case ADDPAYEMENT:
                    return Response.ok().entity(addpayement(obj).toString(INDENT_FACTOR)).build();
                case VALIDATE:
                    return Response.ok().entity(validate(obj).toString(INDENT_FACTOR)).build();
                case CONSULT:
                    return Response.ok().entity(consult(obj).toString(INDENT_FACTOR)).build();
            }
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
        return null;
    }

}
