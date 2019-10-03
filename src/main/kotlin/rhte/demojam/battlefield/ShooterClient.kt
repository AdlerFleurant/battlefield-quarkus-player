package rhte.demojam.battlefield

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType
import javax.ws.rs.core.Response

@Path("/api/hit")
@RegisterRestClient
interface ShooterClient {
    @GET
    @Path("/{player}")
    @Produces(MediaType.TEXT_PLAIN)
    fun shoot(@PathParam("player") shootBy: String): Response
}