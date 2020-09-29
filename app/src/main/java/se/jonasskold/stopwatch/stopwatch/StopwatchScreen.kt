package se.jonasskold.stopwatch.stopwatch

import androidx.compose.foundation.Text
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.viewModel
import androidx.ui.tooling.preview.Preview
import se.jonasskold.stopwatch.ui.StopwatchTheme

@Composable
fun StopwatchScreen() {
    val viewModel = viewModel<StopwatchViewModel>()

    StopwatchTheme {
        Surface {
            Greeting(name = viewModel.name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview
@Composable
fun preview() {
    StopwatchTheme {
        Surface {
            Greeting(name = "Preview")
        }
    }
}