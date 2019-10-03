package rhte.demojam.battlefield

import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType

@Path("api/status")
class Status @Inject constructor(val healthManager: HealthManager) {
    @GET
    @Path("currenthealth")
    @Produces(MediaType.TEXT_PLAIN)
    fun mine(): Int {
        val health = healthManager.currentHealth.get()
        return if (health > 0) {
            health
        } else 0
    }

    @GET
    @Path("killedby")
    @Produces(MediaType.TEXT_PLAIN)
    fun lastHit(): String {
        return healthManager.lastHitBy
    }
}
