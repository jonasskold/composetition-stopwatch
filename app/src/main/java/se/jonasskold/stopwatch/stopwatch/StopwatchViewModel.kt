package se.jonasskold.stopwatch.stopwatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import se.jonasskold.stopwatch.model.Stopwatch
import java.time.Instant

class StopwatchViewModel : ViewModel() {
    private fun now() = Instant.now().toEpochMilli()

    var stopwatch by mutableStateOf(Stopwatch())
    var now by mutableStateOf(now())

    fun resume() {
        val time = now()
        stopwatch = stopwatch.resume(time)
        now = time
    }

    fun stop() {
        val time = now()
        stopwatch = stopwatch.stop(time)
        now = time
    }

    fun reset() {
        stopwatch = stopwatch.reset()
    }
}