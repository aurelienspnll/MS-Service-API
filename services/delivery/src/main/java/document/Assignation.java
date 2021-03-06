package document;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

import static document.Handler.*;

@Path("/delivery")
@Produces(MediaType.APPLICATION_JSON)
public class Assignation {

    private static final int INDENT_FACTOR = 2;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response process(String input) {
        JSONObject obj = new JSONObject(input);
        try {
            switch (EVENT.valueOf(obj.getString("event"))) {
                case DELIVER:
                    return Response.ok().entity(deliver(obj).toString(INDENT_FACTOR)).build();
                case COMPLETE:
                    return Response.ok().entity(complete(obj).toString(INDENT_FACTOR)).build();
                case ASSIGN:
                    return Response.ok().entity(assign(obj).toString(INDENT_FACTOR)).build();
                case CONSULT:
                    return Response.ok().entity(consult(obj).toString(INDENT_FACTOR)).build();
                case LIST:
                    return Response.ok().entity(list().toString(INDENT_FACTOR)).build();
                case LISTCOMPLETED:
                    return Response.ok().entity(listCompleted().toString(INDENT_FACTOR)).build();
                case LISTNOTCOMPLETED:
                    return Response.ok().entity(listNotCompleted().toString(INDENT_FACTOR)).build();
                case DELETE:
                    return Response.ok().entity(delete(obj).toString(INDENT_FACTOR)).build();
                case CLEAN:
                    return Response.ok().entity(deleteAll().toString(INDENT_FACTOR)).build();
                case TRACK:
                    return Response.ok().entity(tracking(obj).toString(INDENT_FACTOR)).build();
                case PROBLEM:
                    return Response.ok().entity(problem(obj).toString(INDENT_FACTOR)).build();

            }
        }catch(Exception e) {
            JSONObject error = new JSONObject().put("error", e.toString());
            return Response.status(400).entity(error.toString(INDENT_FACTOR)).build();
        }
        return null;
    }

}