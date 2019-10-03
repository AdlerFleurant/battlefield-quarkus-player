package rhte.demojam.battlefield

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.event.Observes

import io.quarkus.runtime.ShutdownEvent
import io.quarkus.runtime.StartupEvent
import org.eclipse.microprofile.config.inject.ConfigProperty
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.logging.Level
import java.util.logging.Logger
import javax.inject.Inject
import kotlin.system.exitProcess

@ApplicationScoped
open class AppLifecycle @Inject constructor(@ConfigProperty(name = "BATTLEFIELD_HIT_PERIOD_MS") private val period: Long) {

    companion object {
        @JvmField
        val LOGGER: Logger = Logger.getLogger(AppLifecycle::class.java.simpleName)
    }

    private lateinit var threadPool: ScheduledExecutorService
    @Inject
    internal open lateinit var shoot: Shoot

    open fun onStart(@Observes event: StartupEvent) {
        LOGGER.log(Level.INFO, "Starting App")
        threadPool = Executors.newScheduledThreadPool(1)
        threadPool.scheduleAtFixedRate({
            shoot.shoot()
        }, 0, period, TimeUnit.MILLISECONDS)
    }

    open fun onStop(@Observes event: ShutdownEvent) {
        LOGGER.log(Level.INFO, "Shutdown App")
        threadPool.shutdownNow()
        Thread {
            exitProcess(0)
        }.start()
    }
}