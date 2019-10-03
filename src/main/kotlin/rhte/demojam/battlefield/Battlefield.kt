package rhte.demojam.battlefield

import org.eclipse.microprofile.config.inject.ConfigProperty
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

@ApplicationScoped
open class Battlefield {
    @Inject
    @ConfigProperty(name = "BATTLEFIELD_PLAYER_URLS")
    open lateinit var urls: List<String>
}