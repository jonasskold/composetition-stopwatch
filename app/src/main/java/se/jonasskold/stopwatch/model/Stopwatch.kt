package se.jonasskold.stopwatch.model

data class Stopwatch(
    val startedAt: Long? = null,
    val offset: Long = 0
) {
    fun totalAt(time: Long): Long {
        val running = startedAt?.let { time - it } ?: 0
        return running + offset
    }

    fun resume(time: Long): Stopwatch {
        return if (startedAt == null) copy(startedAt = time) else this
    }

    fun stop(time: Long): Stopwatch {
        return copy(
            startedAt = null,
            offset = totalAt(time)
        )
    }

    fun reset(): Stopwatch {
        return copy(
            startedAt = null,
            offset = 0
        )
    }

    val isRunning: Boolean
        get() = startedAt != null
}