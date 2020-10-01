package se.jonasskold.stopwatch.model

data class Lap(
        val startedAt: Long? = null,
        val offset: Long = 0,
        val startOffset: Long = 0
) {
    fun totalAt(time: Long): Long {
        val running = startedAt?.let { time - it } ?: 0
        return running + offset
    }

    fun splitTimeAt(time: Long): Long {
        return totalAt(time) + startOffset
    }

    fun resume(time: Long): Lap {
        return if (startedAt == null) copy(startedAt = time) else this
    }

    fun stop(time: Long): Lap {
        return copy(
                startedAt = null,
                offset = totalAt(time)
        )
    }

    val isRunning: Boolean
        get() = startedAt != null
}