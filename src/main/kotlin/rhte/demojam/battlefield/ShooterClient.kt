package rhte.demojam.battlefield

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import javax.ws.rs.Path
import javax.ws.rs.core.Response

@Path("api/hit")
@RegisterRestClient
interface ShooterClient {

    @GET
    open fun shoot(@HeaderParam("byplayer") shootByte: String): Response
}