package se.jonasskold.stopwatch.stopwatch

import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.FirstBaseline
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import kotlinx.coroutines.ObsoleteCoroutinesApi
import se.jonasskold.stopwatch.model.Lap
import se.jonasskold.stopwatch.ui.StopwatchTheme
import se.jonasskold.stopwatch.ui.green
import se.jonasskold.stopwatch.ui.red

@ObsoleteCoroutinesApi
@Composable
fun StopwatchScreen() {
    val viewModel = viewModel<StopwatchViewModel>()

    StopwatchTheme {
        Surface {
            Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
            ) {
                Time(
                        time = viewModel.stopwatch.totalAt(viewModel.now),
                        modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                )
                Laps(
                        viewModel.stopwatch.laps,
                        viewModel.now,
                        modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                                .weight(2f)
                )
                StopwatchButtons(
                        isRunning = viewModel.stopwatch.isRunning,
                        onClickResume = { viewModel.resume() },
                        onClickStop = { viewModel.stop() },
                        onClickReset = { viewModel.reset() },
                        onClickLap = { viewModel.lap() }
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
        onClickReset: () -> Unit = {},
        onClickLap: () -> Unit = {},
        modifier: Modifier = Modifier
) {
    Row(
            modifier = modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
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
                onClick = if (isRunning) onClickLap else onClickReset,
                modifier = Modifier
                        .preferredWidth(128.dp)
                        .padding(8.dp)
        ) {
            Text(text = if (isRunning) "Lap" else "Reset")
        }
    }
}

@Composable
fun Time(
        time: Long,
        modifier: Modifier = Modifier,
        style: TextStyle = MaterialTheme.typography.h1,
        millisStyle: TextStyle = MaterialTheme.typography.h3
) {
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
            modifier = modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
                horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                    text = "$hours$minutes$seconds",
                    style = style,
                    modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .alignWithSiblings(FirstBaseline)
            )
            Text(
                    text = millis,
                    style = millisStyle,
                    modifier = Modifier.alignWithSiblings(FirstBaseline)
            )
        }
    }
}

@Composable
fun Laps(
        laps: List<Lap>,
        now: Long,
        modifier: Modifier = Modifier
) {
    ScrollableColumn(
            modifier = modifier
    ) {
        Text(text = "Laps", modifier = Modifier.align(Alignment.CenterHorizontally))
        Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text("Lap time")
            Text("Split time")
        }
        laps.forEach { lap ->
            Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Time(
                        time = lap.totalAt(now),
                        style = MaterialTheme.typography.h5,
                        millisStyle = MaterialTheme.typography.h6
                )
                Time(
                        time = lap.splitTimeAt(now),
                        style = MaterialTheme.typography.h5,
                        millisStyle = MaterialTheme.typography.h6
                )
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    StopwatchTheme {
        Surface {
            Column {
                Time(time = 83450, modifier = Modifier.fillMaxWidth())
                StopwatchButtons()
            }
        }
    }
}