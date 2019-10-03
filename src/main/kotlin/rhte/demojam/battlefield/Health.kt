package rhte.demojam.battlefield

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("api/status/currenthealth")
open class Health {
    @Inject
    open lateinit var healthManager: HealthManager

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    fun mine(): Int {
        return healthManager.current.get()
    }
}