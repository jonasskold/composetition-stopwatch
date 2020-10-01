package se.jonasskold.stopwatch.stopwatch

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import se.jonasskold.stopwatch.model.Stopwatch
import se.jonasskold.stopwatch.ui.StopwatchTheme

@ObsoleteCoroutinesApi
@Composable
fun StopwatchScreen() {
    val viewModel = viewModel<StopwatchViewModel>()

    StopwatchTheme {
        Surface {
            Column {
                Time(time = viewModel.stopwatch.totalAt(viewModel.now))
                StopwatchButtons(
                        stopwatch = viewModel.stopwatch,
                        onClickResume = { viewModel.resume() },
                        onClickStop = { viewModel.stop() },
                        onClickReset = { viewModel.reset() }
                )
            }
        }
    }
}

@Composable
fun StopwatchButtons(
        stopwatch: Stopwatch,
        onClickResume: () -> Unit,
        onClickStop: () -> Unit,
        onClickReset: () -> Unit
) {
    Button(onClick = if (stopwatch.isRunning) onClickStop else onClickResume) {
        Text(text = if (!stopwatch.isRunning) "Start" else "Stop")
    }
    Button(onClick = onClickReset) {
        Text(text = "Reset")
    }
}

@Composable
fun Time(time: Long) {
    val hours: Long = remember(time) { time / 360000 }
    val minutes: Long = remember(time) { (time % 360000) / 60000 }
    val seconds: Long = remember(time) { (time % 60000) / 1000 }
    val millis: Long = remember(time) { time % 1000 }
    Text(text = "$hours:$minutes:$seconds.$millis")
}

@Preview
@Composable
fun preview() {
    StopwatchTheme {
        Surface {
            Time(time = 1500)
        }
    }
}