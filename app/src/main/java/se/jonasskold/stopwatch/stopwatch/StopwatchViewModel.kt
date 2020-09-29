package se.jonasskold.stopwatch.stopwatch

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class StopwatchViewModel : ViewModel() {
    var name by mutableStateOf("view model")
}