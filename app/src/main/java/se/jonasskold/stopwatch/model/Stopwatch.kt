package se.jonasskold.stopwatch.model

data class Stopwatch(
        val startedAt: Long? = null,
        val offset: Long = 0,
        val laps: List<Lap> = listOf(Lap())
) {
    fun totalAt(time: Long): Long {
        val running = startedAt?.let { time - it } ?: 0
        return running + offset
    }

    fun resume(time: Long): Stopwatch {
        return if (startedAt == null) {
            copy(startedAt = time, laps = resumeCurrentLap(time))
        } else this
    }

    fun stop(time: Long): Stopwatch {
        return copy(
                startedAt = null,
                offset = totalAt(time),
                laps = stopCurrentLap(time)
        )
    }

    fun reset(): Stopwatch {
        return copy(
                startedAt = null,
                offset = 0,
                laps = listOf(Lap())
        )
    }

    private fun resumeCurrentLap(time: Long): List<Lap> {
        val last = laps.last().resume(time)
        return laps.dropLast(1).plus(last)
    }

    private fun stopCurrentLap(time: Long): List<Lap> {
        val last = laps.last().stop(time)
        return laps.dropLast(1).plus(last)
    }

    fun lap(time: Long): Stopwatch {
        val split = totalAt(time)
        val previousLaps = stopCurrentLap(time)
        val newLap = Lap(startedAt = time, startOffset = split)
        return copy(
                laps = previousLaps.plus(newLap)
        )
    }

    val isRunning: Boolean
        get() = startedAt != null
}