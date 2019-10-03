package rhte.demojam.battlefield

import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.PathParam
import javax.ws.rs.Produces
import javax.ws.rs.core.MediaType


@Path("/api/hit")
class Hit {
    companion object {
        @JvmField
        val LOGGER: Logger = Logger.getLogger(Hit::class.java.simpleName)
    }

    @Inject
    lateinit var healthManager: HealthManager

    @GET
    @Path("/{player}")
    @Produces(MediaType.TEXT_PLAIN)
    fun me(@PathParam("player") hitter: String): Int {
        LOGGER.log(Level.INFO, "Hit by $hitter")
        val health = healthManager.decreaseHealth(hitter)

        LOGGER.log(Level.INFO, "Health: $health")
        return health
    }
}