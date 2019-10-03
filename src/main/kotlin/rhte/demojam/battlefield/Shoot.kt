package rhte.demojam.battlefield

import io.quarkus.scheduler.Scheduled
import org.eclipse.microprofile.config.inject.ConfigProperty
import org.eclipse.microprofile.rest.client.RestClientBuilder
import org.jboss.logmanager.Level
import java.net.URI
import java.util.concurrent.TimeUnit
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
    open fun init(){
        killers = battlefield.urls.map {
            it to RestClientBuilder.newBuilder().baseUri(URI("http://$it"))
                    .build(ShooterClient::class.java)
        }.toList()
    }

    @Scheduled(every = "{BATTLEFIELD_HIT_PERIOD}", delayUnit = TimeUnit.MILLISECONDS)
    open fun shoot(){
        val targetIndex = Random.nextInt(killers.size)
        val target = killers[targetIndex]

        val shootResult = target.second.shoot(myName)

        LOGGER.log(Level.DEBUG,"Player index: $targetIndex; total: ${battlefield.urls.size}")
        LOGGER.log(Level.INFO, "Hit player: ${target.first}")

        if(shootResult.status != 200){
            LOGGER.log(Level.INFO, "Failed to hit player: $target.- HTTP ${shootResult.statusInfo.reasonPhrase}")
        }
    }
}