package se.jonasskold.stopwatch.stopwatch

import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.FirstBaseline
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import se.jonasskold.stopwatch.ui.StopwatchTheme
import se.jonasskold.stopwatch.ui.green
import se.jonasskold.stopwatch.ui.red

@ObsoleteCoroutinesApi
@Composable
fun StopwatchScreen() {
    val viewModel = viewModel<StopwatchViewModel>()

    StopwatchTheme {
        Surface {
            Column {
                Time(time = viewModel.stopwatch.totalAt(viewModel.now))
                StopwatchButtons(
                        isRunning = viewModel.stopwatch.isRunning,
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
        isRunning: Boolean = false,
        onClickResume: () -> Unit = {},
        onClickStop: () -> Unit = {},
        onClickReset: () -> Unit = {}
) {
    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
    ) {
        Button(
                onClick = if (isRunning) onClickStop else onClickResume,
                modifier = Modifier
                        .preferredWidth(128.dp)
                        .padding(8.dp),
                backgroundColor = if (isRunning) red else green
        ) {
            Text(
                    text = if (!isRunning) "Start" else "Stop",
                    color = Color.White)
        }
        Button(
                onClick = onClickReset,
                modifier = Modifier
                        .preferredWidth(128.dp)
                        .padding(8.dp)
        ) {
            Text(text = "Reset")
        }
    }
}

@Composable
fun Time(time: Long) {
    val hours: String = remember(time) {
        val h = time / 3600000
        if (h == 0L) "" else "$h:"
    }
    val minutes: String = remember(time) {
        val m = (time % 3600000) / 60000
        if (m == 0L) "" else "$m:"
    }
    val seconds: String = remember(time) { "${(time % 60000) / 1000}" }
    val millis: String = remember(time) {
        val ms = (time % 1000) / 10
        if (ms < 10) "0$ms" else "$ms"
    }
    Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
    ) {
        Text(
                text = "$hours$minutes$seconds",
                style = MaterialTheme.typography.h1,
                modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .alignWithSiblings(FirstBaseline)
        )
        Text(
                text = millis,
                style = MaterialTheme.typography.h3,
                modifier = Modifier
                        .alignWithSiblings(FirstBaseline)
        )

    }
}

@Preview
@Composable
fun preview() {
    StopwatchTheme {
        Surface {
            Column {
                Time(time = 83450)
                StopwatchButtons()
            }
        }
    }
}