package rhte.demojam.battlefield

import org.eclipse.microprofile.config.inject.ConfigProperty
import java.io.File
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import kotlin.system.exitProcess

@ApplicationScoped
open class FuneralService {
    @Inject
    @ConfigProperty(name = "TERMINATION_LOG", defaultValue = "/dev/termination-log")
    internal open lateinit var terminationLog: String

    open fun die(message: String) {
        File(terminationLog).writeText(message)
        exitProcess(0)
    }
}