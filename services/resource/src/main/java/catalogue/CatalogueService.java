package catalogue;

import javax.ws.rs.*;



@Path("/helloworld")
public class CatalogueService {

    @GET
    @Produces("text/plain")
    public String getClichedMessage() {
        return "Hello World";
    }
}
