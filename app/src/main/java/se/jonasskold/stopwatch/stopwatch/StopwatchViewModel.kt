package se.jonasskold.stopwatch.stopwatch

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import se.jonasskold.stopwatch.model.Stopwatch
import java.time.Instant

@ObsoleteCoroutinesApi
class StopwatchViewModel : ViewModel() {
    private var tickerChannel: ReceiveChannel<Unit>? = null
    private fun now() = Instant.now().toEpochMilli()

    var stopwatch by mutableStateOf(Stopwatch())
    var now by mutableStateOf(now())

    override fun onCleared() {
        endTicker()
    }

    fun resume() {
        val time = now()
        stopwatch = stopwatch.resume(time)
        now = time
        startTicker()
    }

    fun stop() {
        val time = now()
        stopwatch = stopwatch.stop(time)
        now = time
        endTicker()
    }

    fun reset() {
        stopwatch = stopwatch.reset()
        endTicker()
    }

    private fun startTicker() {
        if (tickerChannel == null) {
            tickerChannel = ticker(37, 0, viewModelScope.coroutineContext)
        }
        tickerChannel?.let {
            viewModelScope.launch {
                try {
                    it.consumeAsFlow().collect {
                        now = now()
                    }
                } catch (e: Throwable) {
                    Log.e("Viewmodel","ticker flow", e)
                }
            }
        }
    }

    private fun endTicker() {
        tickerChannel?.cancel()
        tickerChannel = null
    }
}