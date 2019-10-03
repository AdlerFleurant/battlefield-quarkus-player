package rhte.demojam.battlefield

import org.eclipse.microprofile.config.inject.ConfigProperty

import javax.annotation.PostConstruct
import java.util.concurrent.atomic.AtomicInteger
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
open class HealthManager {
    @Inject
    @ConfigProperty(name = "BATTLEFIELD_MAX_HEALTH")
    internal open var max: Int = 0

    open val current = AtomicInteger()

    open var lasthitby: String? = null
//        private set

    //Return non-negative value
    open val health: Int
        get() = Math.max(current.toInt(), 0)

    @PostConstruct
    open fun postConstruct() {
        current.set(max)
    }

    //Decrease health by one. Save the player name if zero health is reached.
    open fun decreaseHealth(hitByPlayer: String): Int {
        val health = current.decrementAndGet()
        if (health == 0) lasthitby = hitByPlayer

        return health
    }

    open fun setCurrent(current: Int) {
        this.current.set(current)
    }

    override fun toString(): String {
        return "HealthManager{" +
                "max=" + max +
                ", current=" + current +
                ", lasthitby='" + lasthitby + '\''.toString() +
                '}'.toString()
    }
}
