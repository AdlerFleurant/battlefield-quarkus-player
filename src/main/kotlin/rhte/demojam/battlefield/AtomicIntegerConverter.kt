package rhte.demojam.battlefield

import org.eclipse.microprofile.config.spi.Converter
import java.util.concurrent.atomic.AtomicInteger

class AtomicIntegerConverter: Converter<AtomicInteger> {
    override fun convert(value: String?): AtomicInteger {
        val atomicValue = AtomicInteger()

        atomicValue.set(Integer.valueOf(value))

        return atomicValue
    }
}