package rhte.demojam.battlefield

import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.RestClientBuilder
import java.net.URI
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger
import javax.annotation.PostConstruct
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import kotlin.random.Random

@ApplicationScoped
open class Shoot {
    companion object {
        @JvmField
        val LOGGER: Logger = Logger.getLogger(Hit::class.java.simpleName)
    }

    @Inject
    open lateinit var battlefield: Battlefield

    open lateinit var killers: List<Pair<String, ShooterClient>>

    @Inject
    @ConfigProperty(name = "BATTLEFIELD_PLAYER_NAME")
    open lateinit var myName: String

    @PostConstruct
    open fun init() {
        LOGGER.log(Level.INFO, "Initializing Targets")
        killers = battlefield.urls.map {
            it to RestClientBuilder.newBuilder().baseUri(URI("http://$it"))
                    .connectTimeout(1, TimeUnit.SECONDS)
                    .readTimeout(1, TimeUnit.SECONDS)
                    .build(ShooterClient::class.java)
        }.toList()

        LOGGER.log(Level.INFO, "All targets location acquired")
    }

    open fun shoot() {

        val targetIndex = Random.nextInt(killers.size)
        val target = killers[targetIndex]

        LOGGER.log(Level.INFO, "Acquired target: ${target.first}")

        try {
            val shootResult = target.second.shoot(myName)

            LOGGER.log(Level.FINE, "Player index: $targetIndex; total: ${battlefield.urls.size}")
            LOGGER.log(Level.INFO, "Hit player: ${target.first}")

            if (shootResult.status != 200) {
                LOGGER.log(Level.INFO, "Failed to hit player: $target.- HTTP ${shootResult.statusInfo.reasonPhrase}")
            }
        }catch (ex: Exception){
            LOGGER.log(Level.SEVERE, "There was an error shooting at ${target.first} with: ${ex.message}")
        }
    }
}