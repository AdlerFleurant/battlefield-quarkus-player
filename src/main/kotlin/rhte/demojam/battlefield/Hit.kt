package rhte.demojam.battlefield

import org.eclipse.microprofile.config.inject.ConfigProperty
import java.nio.file.Files
import java.nio.file.Paths
import java.util.logging.FileHandler
import java.util.logging.Level
import javax.ws.rs.Path
import java.util.logging.Logger
import javax.inject.Inject
import javax.ws.rs.GET
import javax.ws.rs.HeaderParam
import kotlin.system.exitProcess
import java.io.FileWriter
import java.io.BufferedWriter



@Path("api/hit")
class Hit {
    companion object {
        @JvmField
        val LOGGER: Logger = Logger.getLogger(Hit::class.java.simpleName)
        @JvmField
        val DEAD_MAN_WISH: Logger = Logger.getLogger("LAST_WISH")

    }

    @Inject
    lateinit var healthManager: HealthManager

    @Inject
    @ConfigProperty(name = "TERMINATION_LOG", defaultValue = "/dev/termination-log")
    lateinit var terminationLog: String

    @GET
    fun me(@HeaderParam("byplayer") hitter: String): Int {
        LOGGER.log(Level.INFO, "Hit by $hitter")
        val health = healthManager.decreaseHealth(hitter)

        LOGGER.log(Level.INFO, "Health: $health")

        if (health == 0) {
            LOGGER.log(Level.INFO, "Shutting Down...")
            val writer = BufferedWriter(FileWriter(terminationLog))
            writer.write(healthManager.lasthitby!!)

            writer.close()

            exitProcess(0)
        }

        return health
    }
}