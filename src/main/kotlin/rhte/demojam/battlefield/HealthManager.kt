package rhte.demojam.battlefield

import org.eclipse.microprofile.config.inject.ConfigProperty

import java.util.concurrent.atomic.AtomicInteger
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
open class HealthManager @Inject constructor(@ConfigProperty(name = "BATTLEFIELD_MAX_HEALTH") internal open val currentHealth: AtomicInteger, private val funeralService: FuneralService) {

    private var killedBy: String = ""

    internal open var lastHitBy: String = ""

    open fun decreaseHealth(hitByPlayer: String): Int {
        val health = currentHealth.decrementAndGet()
        lastHitBy = hitByPlayer
        if (health == 0) {
            killedBy = hitByPlayer
            Thread {
                funeralService.die(killedBy)
            }.start()
        }

        return health
    }
}
